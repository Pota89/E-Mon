package angelini.domotica.data.db

import androidx.room.*

@Dao
interface RoomDao {

    @Insert()
    suspend fun insert(vararg rooms: Room)

    @Query("SELECT * FROM room")
    fun getAll(): List<Room>

    @Query("DELETE FROM room")
    suspend fun deleteAll()
}