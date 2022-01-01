package angelini.domotica.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DeviceDao {

    @Insert()
    fun insert(vararg devices: Device)

    @Query("SELECT * FROM device")
    fun getAll(): LiveData<List<Device>>

    @Query("SELECT * FROM device WHERE roomType = :roomType AND roomNumber = :roomNumber")
    fun getRoomDevices(roomType: RoomType, roomNumber: Int): LiveData<List<Device>>

    //DISTINCT clause because to avoid duplicates on list
    @Query("SELECT DISTINCT roomType,roomNumber FROM device")
    fun getAllRooms(): LiveData<List<Room>>

    @Query("DELETE FROM device")
    fun deleteAll()
}