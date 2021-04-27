package angelini.domotica.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDao {
    @Query("SELECT * FROM room")
    fun getAll(): List<Room>
}