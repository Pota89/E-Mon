package angelini.domotica.repository

import android.util.Log
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.network.INetworkClient
import angelini.domotica.repository.network.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

/**
 * Sorgente principale d'informazioni per l'applicativo
 *
 * Si occupa di astrarre i dettagli riguardanti il reperimento e la gestione dei dati
 * utilizzati dall'applicativo.
 * Riduce al minimo le richieste di aggiornamento dello stato dell'abitazione mantenendo
 * una copia in locale allineata allo stato del server MQTT tramite aggiornamenti dei
 * singoli feeds.
 *
 * @property database database usato come cache per i dati provenienti dalla rete
 * @property network classe di rete, definito come interfaccia per permettere il testing
 * della classe Repository tramite mockup
 */
class Repository(database:CacheDatabase, network: INetworkClient) {

    private val db=database
    private val networkClient=network
    private val parser= Parser("")

    val devicesList: Flow<List<Device>> =db.deviceDao().getAllDevices()
    val roomsList: Flow<List<Room>> =db.deviceDao().getRoomList()

    /**
     * Restituisce il riferimento alla lista di Device della singola Room
     *
     * Il tipo di ritorno è Flow, si comporta similmente a un puntatore all'ultimo elemento
     * di una coda e permette di mantenere all'interno della variabile la lista
     * sempre aggiornata
     *
     * @property room Room di cui si vuole ottenere i Device presenti
     */
    fun getRoomDevicesList(room:Room): Flow<List<Device>> {
        return db.deviceDao().getRoomDevices(room.type,room.number)
    }

    //for each received message from network, update database
    init {
        networkClient.onMessageArrived={ topic, message ->
            Log.i("Test","topic $topic message $message")
            MainScope().launch(Dispatchers.IO) {
                val list=parser.decode(message)
                val userDao = db.deviceDao()
                for(element in list){
                    userDao.insert(element)
                }
            }
        }
    }

    /**
     * Connessione del repository alla sorgente dati remota
     *
     * @property username utente per l'accesso al server MQTT
     * @property password password per l'accesso al server MQTT
     */
    suspend fun connect(username:String,password:String):Boolean {
        if(networkClient.isConnected())
            return false

        parser.rootname=username

        val userDao = db.deviceDao()
        userDao.deleteAll()

        val connectionResult:Boolean=suspendCoroutine { cont ->
            networkClient.onConnectionSuccess={cont.resumeWith(Result.success(true))}
            networkClient.onConnectionFailure={cont.resumeWith(Result.success(false))}
            networkClient.connect(username,password)
        }

        if (!connectionResult)
            return false

        val subscribeAllFeedsResults:Boolean=suspendCoroutine { cont ->
            networkClient.onSubscribeSuccess={cont.resumeWith(Result.success(true))}
            networkClient.onSubscribeFailure={cont.resumeWith(Result.success(false))}
            networkClient.subscribe(parser.subscribeAllFeeds())
        }

        if (!subscribeAllFeedsResults)
            return false

        val requestAllResults:Boolean=suspendCoroutine { cont ->
            networkClient.onPublishSuccess={cont.resumeWith(Result.success(true))}
            networkClient.onPublishFailure={cont.resumeWith(Result.success(false))}
            networkClient.publish(parser.requestAllFeedsData(),"")
        }

        if (!requestAllResults)
            return false

        return true
    }

    /**
     * Aggiorna lo stato in remoto di un singolo Device
     *
     * Il repository manda la richiesta di aggiornamento al server MQTT, non aggiorna direttamente
     * il database cache ma attende la risposta dal server MQTT
     *
     * @property device Device di cui aggiornare il valore in remoto
     */
    suspend fun update(device: Device):Boolean{
        return suspendCoroutine { cont ->
            networkClient.onPublishSuccess={cont.resumeWith(Result.success(true))}
            networkClient.onPublishFailure={cont.resumeWith(Result.success(false))}
            networkClient.publish(parser.encodeTopic(device),device.value.toString())
        }
    }

    /**
     * Effettua la disconnessione dal server MQTT
     */
    suspend fun disconnect() {
        return suspendCoroutine { cont ->
            networkClient.onDisconnectSuccess={cont.resumeWith(Result.success(Unit))}
            networkClient.onDisconnectFailure={cont.resumeWith(Result.success(Unit))}
            networkClient.disconnect()
        }
    }

    /**
     * Verifica se il server MQTT è connesso
     */
    fun isConnected():Boolean{
        return networkClient.isConnected()
    }
}