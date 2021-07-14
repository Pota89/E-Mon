package angelini.domotica.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

data class Room (
    @ColumnInfo(name = "roomType")val type: RoomType = RoomType.UNKNOWN,
    @ColumnInfo(name = "roomNumber")val number:Int=0,
)