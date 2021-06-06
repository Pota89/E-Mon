package angelini.domotica.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DeviceDao {

    @Insert()
    suspend fun insert(vararg devices: Device)

    @Query("SELECT * FROM device")
    fun getAll(): LiveData<List<Device>>

    @Query("DELETE FROM device")
    suspend fun deleteAll()
}