package angelini.domotica.repository.datatypes

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(primaryKeys = ["roomType", "roomNumber", "deviceType", "deviceNumber"])
data class Device (
    @NonNull @Embedded val room: Room = Room(),
    @NonNull @ColumnInfo(name = "deviceType")val type: DeviceType = DeviceType.UNKNOWN,
    @NonNull @ColumnInfo(name = "deviceNumber")val number:Int=0,
    @NonNull @ColumnInfo(name = "deviceValue")val value:Int=0
)