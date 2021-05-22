package angelini.domotica.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RoomDao {

    @Insert()
    suspend fun insert(vararg rooms: Room)

    @Query("SELECT * FROM room")
    fun getAll(): LiveData<List<Room>>

    @Query("DELETE FROM room")
    suspend fun deleteAll()
}