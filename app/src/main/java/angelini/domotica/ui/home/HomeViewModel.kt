package angelini.domotica.ui.home

import androidx.lifecycle.AndroidViewModel
import angelini.domotica.MainApplication
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeViewModel(application: MainApplication) : AndroidViewModel(application) {

    //private val _rooms = application.getRepository().roomsList

/*
    //TODO remove repository detach code
    private lateinit var _rooms:LiveData<List<Room>>
    init {
        val list: List<Room> = emptyList()
        fun <T : Any?> MutableLiveData<T>.default(initialValue: T) =
            apply { setValue(initialValue) }
        _rooms= MutableLiveData<List<Room>>().default(list)
    }*/

    private val _rooms = flow {
        val lista=mutableListOf<Room>()
        val kitchenRoom= Room(RoomType.KITCHEN,1)
        val loungeRoom= Room(RoomType.LOUNGE,1)

        lista.add(kitchenRoom)
        lista.add(loungeRoom)
        emit(lista)
    }

    val rooms: Flow<List<Room>>
        get() = _rooms
}