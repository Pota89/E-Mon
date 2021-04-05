package angelini.domotica.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Repository{

    private val _roomList = mutableListOf<Room>()
    val roomList: List<Room>
        get() = _roomList
}