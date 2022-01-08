package angelini.domotica.data.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CacheDatabaseTest : TestCase() {

    private lateinit var db:CacheDatabase
    private lateinit var dao:DeviceDao

    //private objects useful for tests
    private val kitchenRoom=Room(RoomType.KITCHEN,1)
    private val kitchenLampOne=Device(kitchenRoom,DeviceType.LAMP,1,0)
    private val kitchenLampTwo=Device(kitchenRoom,DeviceType.LAMP,2,0)
    private val kitchenTemperatureOne=Device(kitchenRoom,DeviceType.TEMPERATURE,1,0)

    private val loungeRoom=Room(RoomType.LOUNGE,1)
    private val loungeTemperatureOne=Device(loungeRoom,DeviceType.TEMPERATURE,1,20)

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db=androidx.room.Room.inMemoryDatabaseBuilder(context,CacheDatabase::class.java).build()
        dao=db.deviceDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    /**
     * Verifica se gli inserimenti sono conteggiati correttamente
     */
    @ExperimentalCoroutinesApi
    @Test
    fun  checkDeviceInsert()=runTest{
        val deviceListFlow: Flow<List<Device>> = dao.getAll()
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
        val deviceListFlow: Flow<List<Device>> = dao.getAll()
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
        val deviceListFlow: Flow<List<Device>> = dao.getAll()
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

}