package angelini.domotica.ui.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import angelini.domotica.MainApplication
import angelini.domotica.data.db.Device
import angelini.domotica.data.db.Room

class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val _rooms = getApplication<MainApplication>().getRepository().roomsList

    val rooms: LiveData<List<Room>>
        get() = _rooms
}