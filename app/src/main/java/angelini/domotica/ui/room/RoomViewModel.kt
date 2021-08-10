package angelini.domotica.ui.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import angelini.domotica.MainApplication
import angelini.domotica.data.db.Device
import angelini.domotica.data.db.Room
import angelini.domotica.data.db.RoomType

class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = getApplication<MainApplication>().getRepository()

    fun getRoomDevices(roomType: RoomType, roomNumber: Int): LiveData<List<Device>>{
           return repository.getRoomDevices(roomType,roomNumber)
    }
}