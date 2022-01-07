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

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db=androidx.room.Room.databaseBuilder(context,CacheDatabase::class.java,"cache").build()
        dao=db.deviceDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun  checkDeviceInsert()=runTest{
        val deviceListFlow: Flow<List<Device>> = dao.getAll()
        assertEquals(0, deviceListFlow.first().size)

        val kitchenRoom=Room(RoomType.KITCHEN,0)
        val lampKitchen=Device(kitchenRoom,DeviceType.LAMP,1,0)
        dao.insert(lampKitchen)
        assertEquals(1, deviceListFlow.first().size)

    }
}