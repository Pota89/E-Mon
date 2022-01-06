package angelini.domotica.data.db

import android.content.Context
import androidx.lifecycle.LiveData
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
        db=androidx.room.Room.databaseBuilder(context,CacheDatabase::class.java,"cache").allowMainThreadQueries().build()
        dao=db.deviceDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun  checkDeviceInsert(){
        //assertEquals(0, deviceList.value?.size)

        val kitchenRoom=Room(RoomType.KITCHEN,0)
        val lampKitchen=Device(kitchenRoom,DeviceType.LAMP,1,0)
        //dao.insert(lampKitchen)
        val deviceListLiveData: LiveData<List<Device>> = dao.getAll()
        val deviceList=deviceListLiveData.value
        assertEquals(1, deviceList?.size)
    }
}