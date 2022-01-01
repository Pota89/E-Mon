package angelini.domotica.data.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
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
        db=androidx.room.Room.inMemoryDatabaseBuilder(context,CacheDatabase::class.java).allowMainThreadQueries().build()
        dao=db.deviceDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun  checkDeviceInsert(){
        var deviceList=dao.getAll()
        val kitchenRoom=Room(RoomType.KITCHEN,0)
        val lampKitchen=Device(kitchenRoom,DeviceType.LAMP,1,0)
        dao.insert(lampKitchen)
        deviceList=dao.getAll()
        assertEquals(1, deviceList.value?.size)
    }
}