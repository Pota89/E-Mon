package angelini.domotica.data.db

import androidx.room.Entity
import angelini.domotica.data.RoomType

@Entity(primaryKeys = ["roomType", "roomNumber"])
data class Room (
    val roomType: RoomType = RoomType.UNKNOWN,
    val roomNumber:Int=0,
)