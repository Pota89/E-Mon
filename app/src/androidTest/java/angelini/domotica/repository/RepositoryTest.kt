package angelini.domotica.repository

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.network.MockNetworkClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
        //database=androidx.room.Room.databaseBuilder(context,CacheDatabase::class.java, "cache").build()

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
     * Tenta di stabilire una connessone al repository con credenziali sbagliate
     */
    @ExperimentalCoroutinesApi
    @Test
    fun fakeConnect() {
        runTest {
            repository.connect("wrongtestuser","wrongtestpassword")
            assertFalse(repository.isConnected())
        }
    }

    /**
     * Stabilisce una connessione al repository, registra e imposta il database locale
     */
    @ExperimentalCoroutinesApi
    @Test
    fun connect() {
        runTest {
            repository.connect("testuser","testpassword")
            assertTrue(repository.isConnected())
            val list= repository.devicesList.first()
            assertEquals(7,list.size)//7 elements in mocked network
        }
    }

    @Test
    fun disconnect() {
    }
}

