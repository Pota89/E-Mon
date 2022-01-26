package angelini.domotica.repository

import android.util.Log
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import angelini.domotica.repository.network.INetworkClient
import angelini.domotica.repository.network.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import kotlin.coroutines.suspendCoroutine

class Repository(database:CacheDatabase, network: INetworkClient) {

    private val db=database
    private val networkClient=network
    private val parser= Parser("")

    val devicesList: Flow<List<Device>> =db.deviceDao().getAllDevices()
    val roomsList: Flow<List<Room>> =db.deviceDao().getRoomList()

    fun getRoomDevicesList(room:Room): Flow<List<Device>> {
        return db.deviceDao().getRoomDevices(room.type,room.number)
    }

    //for each received message from network, update database
    init {
        networkClient.onMessageArrived={ _, message ->
            val list=parser.decode(message)
            Log.i("EMon - Repository", "LISTA IN REPOSITORY: $message")
            CoroutineScope(Dispatchers.IO).launch {
                val userDao = db.deviceDao()
                for(element in list){
                    userDao.insert(element)
                }
            }
        }
    }

    /**
     * Connessione del repository alla sorgente dati remota
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

    fun update(device: Device){
        //TODO //parser.encode(device)
    }
    suspend fun disconnect() {
        return suspendCoroutine { cont ->
            networkClient.onDisconnectSuccess={cont.resumeWith(Result.success(Unit))}
            networkClient.onDisconnectFailure={cont.resumeWith(Result.success(Unit))}
            networkClient.disconnect()
        }
    }

    fun isConnected():Boolean{
        return networkClient.isConnected()
    }
}