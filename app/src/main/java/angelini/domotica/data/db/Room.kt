package angelini.domotica.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import angelini.domotica.data.RoomType

@Entity(primaryKeys = ["type", "number"])
data class Room (
    @ColumnInfo(name = "type") val type: RoomType = RoomType.UNKNOWN,
    @ColumnInfo(name = "number") val number:Int=0
)