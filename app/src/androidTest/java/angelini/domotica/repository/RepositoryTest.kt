package angelini.domotica.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.network.MockNetworkClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Verifica del repository con network mocked
 *
 * Gli InstrumentedTests non permettono l'esecuzione di servizi in background con Kotlin.
 * La connessione di rete al Server MQTT viene simulata
 */
@RunWith(AndroidJUnit4::class)
class RepositoryTest {

    private lateinit var database: CacheDatabase
    private lateinit var mockNetworkClient: MockNetworkClient
    private lateinit var repository:Repository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database=androidx.room.Room.inMemoryDatabaseBuilder(context,CacheDatabase::class.java).build()
        mockNetworkClient= MockNetworkClient()
        repository=Repository(database,mockNetworkClient)
    }

    @After
    fun tearDown() {
        if(repository.isConnected())
            repository.disconnect()
    }

    /*
    /**
     * Recupera dal server MQTT mocked la lista dei Device
     */
    @Test
    fun getStandardDevicesList() {
        repository.connect(MQTT_USERNAME, MQTT_PWD)
        repository.devicesList.count()
    }*/

    @Test
    fun getRoomsList() {
    }

    @Test
    fun getRoomDevices() {
    }

    /**
     * Stabilisce una connessione al repository
     */
    @ExperimentalCoroutinesApi
    @Test
    fun connect() {
        runTest {
            repository.connect("testuser","testpassword")
            assertTrue(repository.isConnected())
        }
    }

    @Test
    fun disconnect() {
    }
}

