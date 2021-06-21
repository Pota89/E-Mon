package angelini.domotica.data.db

import androidx.room.Embedded
import androidx.room.Entity
import angelini.domotica.data.DeviceType

@Entity(primaryKeys = ["roomType", "roomNumber", "deviceType", "deviceNumber"])
data class Device (
    @Embedded val room: Room = Room(),
    val deviceType: DeviceType = DeviceType.UNKNOWN,
    val deviceNumber:Int=0,
    val deviceValue:Int=0
)