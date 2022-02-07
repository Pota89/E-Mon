package angelini.domotica.repository

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.network.MOCKED_MQTT_PWD
import angelini.domotica.repository.network.MOCKED_MQTT_USERNAME
import angelini.domotica.repository.network.MockNetworkClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
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
        runBlocking {
            if (repository.isConnected())
                repository.disconnect()
        }
    }

    /**
     * Ottiene un flow con solo le Room a partire dai Device
     *
     * Sono presenti 6 Room nel mock
     */
    @ExperimentalCoroutinesApi
    @Test
    fun getRoomList() {
        runTest {
            repository.connect(MOCKED_MQTT_USERNAME, MOCKED_MQTT_PWD)
            val list= repository.roomsList.first()
            assertEquals(6,list.size)//6 Rooms
        }
    }

    /**
     * Ottiene un flow con i Device presenti in una Room
     *
     * In dettaglio si verifica se sono presenti i 2 sensori di temperatura
     * della Bedroom mocked
     */
    @ExperimentalCoroutinesApi
    @Test
    fun getRoomDeviceList() {
        runTest {
            repository.connect(MOCKED_MQTT_USERNAME, MOCKED_MQTT_PWD)
            val bedroom=Room(RoomType.BEDROOM,1)
            val list= repository.getRoomDevicesList(bedroom).first()
            assertEquals(2,list.size)//2 Devices in mocked bedroom
        }
    }

    /**
     * Verifica il comportamento del Flow con tutti i Devices
     */
    @ExperimentalCoroutinesApi
    @Test
    fun getAllDevices() {
        runTest {
            repository.connect(MOCKED_MQTT_USERNAME, MOCKED_MQTT_PWD)
            val list= repository.devicesList.first()
            assertEquals(7,list.size)//7 elements in mocked network
        }
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
            repository.connect(MOCKED_MQTT_USERNAME, MOCKED_MQTT_PWD)
            assertTrue(repository.isConnected())
            val list= repository.devicesList.first()
            assertEquals(7,list.size)//7 elements in mocked network
        }
    }

    /**
     * Verifica lo stato della connessione e della corretta disconnessione
     */
    @ExperimentalCoroutinesApi
    @Test
    fun disconnect() {
        runTest {
            assertFalse(repository.isConnected())
            repository.connect(MOCKED_MQTT_USERNAME, MOCKED_MQTT_PWD)
            assertTrue(repository.isConnected())
            repository.disconnect()
            assertFalse(repository.isConnected())
        }
    }

    /**
     * Aggiorna il valore di un device
     *
     * Cambia il valore del topic home.bedroom-1-temperature-1 da 19 (default del mock) a 32
     */
    @ExperimentalCoroutinesApi
    @Test
    fun updateDeviceValue() {
        runTest {
            repository.connect(MOCKED_MQTT_USERNAME, MOCKED_MQTT_PWD)
            var list= repository.devicesList.first()
            val refBedroom= Device(Room(RoomType.BEDROOM,1),DeviceType.TEMPERATURE,1,19)
            val bedroom= Device(Room(RoomType.BEDROOM,1),DeviceType.TEMPERATURE,1,32)
            assertTrue(list.contains(refBedroom))
            assertFalse(list.contains(bedroom))

            repository.update(bedroom)
            list= repository.devicesList.first()
            for (item in list)
                Log.i("DeviceList","${item.room.type} ${item.room.number} ${item.type} ${item.number} ${item.value}")

            assertTrue(list.contains(bedroom))
            assertFalse(list.contains(refBedroom))
        }
    }
}

