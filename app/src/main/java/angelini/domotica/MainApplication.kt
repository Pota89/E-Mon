package angelini.domotica

import android.app.Application
import angelini.domotica.repository.MQTT_PWD
import angelini.domotica.repository.MQTT_USERNAME
import angelini.domotica.repository.Repository
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.network.NetworkClient
import kotlinx.coroutines.*

class MainApplication : Application() {
    private lateinit var database:CacheDatabase
    private lateinit var networkClient:NetworkClient
    private lateinit var repository:Repository
    private val applicationIOScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        database=androidx.room.Room.databaseBuilder(
            applicationContext,
            CacheDatabase::class.java, "cache"
        ).build()

        networkClient= NetworkClient(applicationContext)

        repository=Repository(database,networkClient)
        applicationIOScope.launch(Dispatchers.IO) {
            repository.connect(MQTT_USERNAME, MQTT_PWD)
        }
    }

    fun getRepository():Repository{ return repository}

    override fun onTerminate() {
        applicationIOScope.launch(Dispatchers.IO) {
            repository.disconnect()
        }
        super.onTerminate()
    }
}