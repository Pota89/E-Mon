package angelini.domotica.data.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

data class Room (
    @NonNull @ColumnInfo(name = "roomType")val type: RoomType = RoomType.UNKNOWN,
    @NonNull @ColumnInfo(name = "roomNumber")val number:Int=0,
)