package angelini.domotica.ui.room

import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import kotlinx.coroutines.flow.Flow

class RoomViewModel(private val repository: Repository): ViewModel() {
    fun getRoomDevices(roomType: RoomType, roomNumber: Int): Flow<List<Device>> {
        return repository.getRoomDevicesList(Room(roomType,roomNumber))
    }

    suspend fun update(device: Device){
        repository.update(device)
    }
}