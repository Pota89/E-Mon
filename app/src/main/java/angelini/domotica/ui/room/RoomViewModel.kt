package angelini.domotica.ui.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.RoomType

class RoomViewModel(private val repository: Repository): ViewModel() {

    //private val repository = getApplication<MainApplication>().getRepository()

    fun getRoomDevices(roomType: RoomType, roomNumber: Int): LiveData<List<Device>> {

        //TODO remove repository detach code
        val list: List<Device> = emptyList()
        fun <T : Any?> MutableLiveData<T>.default(initialValue: T) =
            apply { setValue(initialValue) }
        return MutableLiveData<List<Device>>().default(list)

        //return repository.getRoomDevices(roomType,roomNumber)
    }
}