package angelini.domotica.ui.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import angelini.domotica.MainApplication
import angelini.domotica.data.db.Device
import angelini.domotica.data.db.RoomType

class RoomViewModel(application: Application) : AndroidViewModel(application) {

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