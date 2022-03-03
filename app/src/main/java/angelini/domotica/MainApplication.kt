package angelini.domotica

import android.app.Application
import android.content.Intent
import android.os.Build
import angelini.domotica.repository.Repository
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.network.NetworkClient
import kotlinx.coroutines.*
import org.eclipse.paho.android.service.MqttService


class MainApplication : Application() {
    private lateinit var database:CacheDatabase
    private lateinit var networkClient:NetworkClient
    private lateinit var repository:Repository
    private val applicationIOScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        //code required to launch services with Android Oreo and upper
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationContext.startForegroundService(Intent(applicationContext, MqttService::class.java))
        } else {
            applicationContext.startService(Intent(applicationContext, MqttService::class.java))
        }

        database=androidx.room.Room.databaseBuilder(
            applicationContext,
            CacheDatabase::class.java, "cache"
        ).build()

        networkClient= NetworkClient(applicationContext)
        repository=Repository(database,networkClient)
    }

    fun getRepository():Repository{ return repository}

    override fun onTerminate() {
        applicationIOScope.launch(Dispatchers.IO) {
            repository.disconnect()
        }
        super.onTerminate()
    }
}