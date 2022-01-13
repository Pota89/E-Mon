package angelini.domotica

import android.app.Application
import angelini.domotica.repository.Repository
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.network.NetworkClient
import angelini.domotica.repository.network.Parser

class MainApplication : Application() {
    private val database=androidx.room.Room.databaseBuilder(
        applicationContext,
    CacheDatabase::class.java, "cache"
    ).build()

    private val networkClient= NetworkClient(applicationContext)

    private lateinit var repository:Repository

    override fun onCreate() {
        super.onCreate()
        repository=Repository(database,networkClient)
        repository.connect()
    }

    fun getRepository():Repository{ return repository}

    override fun onTerminate() {
        repository.disconnect()
        super.onTerminate()
    }
}