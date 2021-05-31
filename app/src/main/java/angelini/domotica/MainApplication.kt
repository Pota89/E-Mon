package angelini.domotica

import android.app.Application
import angelini.domotica.data.Repository

class MainApplication : Application() {
    private lateinit var repository:Repository

    override fun onCreate() {
        super.onCreate()
        repository= Repository(applicationContext)
    }

    fun getRepository():Repository{ return repository}
}