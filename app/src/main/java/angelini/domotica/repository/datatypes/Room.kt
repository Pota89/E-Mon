package angelini.domotica.repository.datatypes

import androidx.annotation.NonNull
import androidx.room.ColumnInfo

data class Room (
    @NonNull @ColumnInfo(name = "roomType")val type: RoomType = RoomType.UNKNOWN,
    @NonNull @ColumnInfo(name = "roomNumber")val number:Int=0,
)