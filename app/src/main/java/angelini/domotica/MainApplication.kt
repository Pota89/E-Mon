package angelini.domotica

import android.app.Application
import android.util.Log
import angelini.domotica.repository.MQTT_PWD
import angelini.domotica.repository.MQTT_USERNAME
import angelini.domotica.repository.Repository
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainApplication : Application() {
    private lateinit var database:CacheDatabase
    private lateinit var networkClient:NetworkClient
    private lateinit var repository:Repository



    override fun onCreate() {
        super.onCreate()
/*
        networkClient= NetworkClient(applicationContext)
        networkClient.onConnectionSuccess={
            networkClient.publish("ExamToGo/feeds/home.bedroom-1-temperature-1","30")
            Log.d("MessaggioTest","Pubblicazione effettuata")
        }
        networkClient.connect(MQTT_USERNAME, MQTT_PWD)
*/

        database=androidx.room.Room.databaseBuilder(
            applicationContext,
            CacheDatabase::class.java, "cache"
        ).build()

        networkClient= NetworkClient(applicationContext)

        repository=Repository(database,networkClient)
        GlobalScope.launch(Dispatchers.IO) {

            repository.connect(MQTT_USERNAME, MQTT_PWD) }

    }

    fun getRepository():Repository{ return repository}

    override fun onTerminate() {
        //repository.disconnect()
        super.onTerminate()
    }
}