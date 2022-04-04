package angelini.domotica

import android.app.Application
import android.content.Intent
import android.os.Build
import angelini.domotica.repository.Repository
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.network.NetworkClient
import kotlinx.coroutines.*
import org.eclipse.paho.android.service.MqttService

/**
 * Punto d'ingresso personalizzato del programma
 *
 * Eredita da Application, è necessario per inizializzare il repository
 * e le sue dipendenze secondo la filosofia Dependency Injection
 */
class MainApplication : Application() {
    private lateinit var database:CacheDatabase
    private lateinit var networkClient:NetworkClient
    private lateinit var repository:Repository

    /**
     * Effettua le inizializzazioni necessarie al repository
     *
     * In dettaglio, provvede a far partire un servizio dedicato alla comunicazione MQTT,
     * inizializzazione del database cache e inizializzazione del repository con gli
     * output precedenti
     */
    override fun onCreate() {
        super.onCreate()

        //code required to launch services with Android Oreo and upper
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            applicationContext.startForegroundService(Intent(applicationContext, MqttService::class.java))

        applicationContext.startService(Intent(applicationContext, MqttService::class.java))

        database=androidx.room.Room.databaseBuilder(
            applicationContext,
            CacheDatabase::class.java, "cache"
        ).build()

        networkClient= NetworkClient(applicationContext)
        repository=Repository(database,networkClient)
    }

    /**
     * Permette di richiamare il repository dal Context
     */
    fun getRepository():Repository{ return repository}

    /**
     * Lancia una coroutine che provvede a disconnettere il repository
     *
     * Internamente al repository viene disconnessa la
     * comunicazione al server MQTT
     */
    override fun onTerminate() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.disconnect()
        }
        super.onTerminate()
    }
}