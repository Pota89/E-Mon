package angelini.domotica.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(primaryKeys = ["roomType", "roomNumber", "deviceType", "deviceNumber"])
data class Device (
    @Embedded val room: Room = Room(),
    @ColumnInfo(name = "deviceType")val type: DeviceType = DeviceType.UNKNOWN,
    @ColumnInfo(name = "deviceNumber")val number:Int=0,
    @ColumnInfo(name = "deviceValue")val value:Int=0
)