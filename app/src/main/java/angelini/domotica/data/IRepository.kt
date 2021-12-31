package angelini.domotica.data

import androidx.lifecycle.LiveData
import angelini.domotica.data.db.Device
import angelini.domotica.data.db.RoomType

interface IRepository {
    fun getRoomDevices(roomType: RoomType, roomNumber: Int): LiveData<List<Device>>
    fun connect()
    fun disconnect()
}