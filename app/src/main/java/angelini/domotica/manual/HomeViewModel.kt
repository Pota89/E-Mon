package angelini.domotica.manual

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import angelini.domotica.data.db.Room
import angelini.domotica.data.RoomType

class HomeViewModel:ViewModel() {
    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>>
        get() = _rooms

    init {
        val tempList= mutableListOf<Room>()
        tempList.add(Room(RoomType.LOUNGE))
        tempList.add(Room(RoomType.BATHROOM))
        tempList.add(Room(RoomType.KITCHEN))
        _rooms.value=tempList
    }
}