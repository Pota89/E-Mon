package angelini.domotica.repository.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test per la verifica delle query al database
 */
@RunWith(AndroidJUnit4::class)
class CacheDatabaseTest {

    private lateinit var db:CacheDatabase
    private lateinit var dao:DeviceDao

    //private objects useful for tests
    private val kitchenRoom= Room(RoomType.KITCHEN,1)
    private val kitchenLampOne= Device(kitchenRoom, DeviceType.LAMP,1,0)
    private val kitchenLampTwo= Device(kitchenRoom, DeviceType.LAMP,2,0)
    private val kitchenTemperatureOne= Device(kitchenRoom, DeviceType.TEMPERATURE,1,0)

    private val loungeRoom= Room(RoomType.LOUNGE,1)
    private val loungeTemperatureOne= Device(loungeRoom, DeviceType.TEMPERATURE,1,20)

    private val bedroomRoom= Room(RoomType.BEDROOM,1)
    private val bedroomLampOne= Device(bedroomRoom, DeviceType.LAMP,1,0)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db=androidx.room.Room.inMemoryDatabaseBuilder(context,CacheDatabase::class.java).build()
        dao=db.deviceDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    /**
     * Verifica se gli inserimenti sono conteggiati correttamente
     */
    @ExperimentalCoroutinesApi
    @Test
    fun  checkDeviceInsert()=runTest{
        val deviceListFlow: Flow<List<Device>> = dao.getAllDevices()
        assertEquals(0, deviceListFlow.first().size)

        dao.insert(kitchenLampOne)
        assertEquals(1, deviceListFlow.first().size)

        dao.insert(loungeTemperatureOne)
        assertEquals(2, deviceListFlow.first().size)
    }

    /**
     * Verifica se i Device duplicati vengono ignorati
     */
    @ExperimentalCoroutinesApi
    @Test
    fun  checkDuplicatedDeviceInsert()=runTest{
        val deviceListFlow: Flow<List<Device>> = dao.getAllDevices()
        assertEquals(0, deviceListFlow.first().size)

        dao.insert(kitchenLampOne)
        assertEquals(1, deviceListFlow.first().size)
        dao.insert(kitchenLampOne) //twice insert of same device
        assertEquals(1, deviceListFlow.first().size)
    }

    /**
     * Verifica la corretta rimozione di tutti i Device dal database
     */
    @ExperimentalCoroutinesApi
    @Test
    fun  checkDeleteAllDevices()=runTest{
        val deviceListFlow: Flow<List<Device>> = dao.getAllDevices()
        assertEquals(0, deviceListFlow.first().size)

        dao.insert(loungeTemperatureOne)
        dao.insert(kitchenLampOne)
        assertEquals(2, deviceListFlow.first().size)

        dao.deleteAll()
        assertEquals(0, deviceListFlow.first().size)
    }

    /**
     * Verifica se vengono selezionati correttamente i Device presenti nelle singole Room
     */
    @ExperimentalCoroutinesApi
    @Test
    fun  checkDeviceSelectionFromRoom()=runTest{
        val kitchenDeviceListFlow: Flow<List<Device>> = dao.getRoomDevices(kitchenRoom.type,kitchenRoom.number)
        assertEquals(0, kitchenDeviceListFlow.first().size)

        val loungeDeviceListFlow: Flow<List<Device>> = dao.getRoomDevices(loungeRoom.type,loungeRoom.number)
        assertEquals(0, loungeDeviceListFlow.first().size)

        dao.insert(kitchenLampOne)
        dao.insert(kitchenLampTwo)
        dao.insert(loungeTemperatureOne)
        assertEquals(2, kitchenDeviceListFlow.first().size)
        assertEquals(1, loungeDeviceListFlow.first().size)

        dao.insert(kitchenTemperatureOne)
        assertEquals(3, kitchenDeviceListFlow.first().size)
        assertEquals(1, loungeDeviceListFlow.first().size)
    }

    /**
     * Verifica il numero di Room presenti a partire dai Device
     */
    @ExperimentalCoroutinesApi
    @Test
    fun  checkRoomList()=runTest{
        val roomListFlow: Flow<List<Room>> = dao.getRoomList()
        assertEquals(0, roomListFlow.first().size)

        dao.insert(kitchenLampOne)
        dao.insert(kitchenTemperatureOne)
        assertEquals(1, roomListFlow.first().size)

        dao.insert(loungeTemperatureOne)
        assertEquals(2, roomListFlow.first().size)

        dao.insert(kitchenLampTwo)
        assertEquals(2, roomListFlow.first().size)

        dao.insert(bedroomLampOne)
        assertEquals(3, roomListFlow.first().size)

    }

}