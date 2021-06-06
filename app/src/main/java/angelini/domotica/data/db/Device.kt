package angelini.domotica.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import angelini.domotica.data.DeviceType
import angelini.domotica.data.RoomType

@Entity(primaryKeys = ["roomType", "roomNumber", "deviceType", "deviceNumber"])
data class Device (
    @ColumnInfo(name = "roomType") val roomType: RoomType = RoomType.UNKNOWN,
    @ColumnInfo(name = "roomNumber") val roomNumber:Int=0,
    @ColumnInfo(name = "deviceType") val deviceType: DeviceType = DeviceType.UNKNOWN,
    @ColumnInfo(name = "deviceNumber") val deviceNumber:Int=0
)