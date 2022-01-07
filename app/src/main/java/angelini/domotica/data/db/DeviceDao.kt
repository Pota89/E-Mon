package angelini.domotica.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Insert()
    fun insert(vararg devices: Device)

    @Query("SELECT * FROM device")
    fun getAll(): Flow<List<Device>>

    @Query("SELECT * FROM device WHERE roomType = :roomType AND roomNumber = :roomNumber")
    fun getRoomDevices(roomType: RoomType, roomNumber: Int): Flow<List<Device>>

    //DISTINCT clause because to avoid duplicates on list
    @Query("SELECT DISTINCT roomType,roomNumber FROM device")
    fun getAllRooms(): Flow<List<Room>>

    @Query("DELETE FROM device")
    fun deleteAll()
}