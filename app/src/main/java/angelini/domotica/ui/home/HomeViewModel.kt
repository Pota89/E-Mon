package angelini.domotica.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeViewModel(private val repository: Repository): ViewModel(){
    init {
        Log.i("HomeViewModel", "We can run HomeViewModel")
    }
/*
    private val _rooms = flow {
        val lista=mutableListOf<Room>()
        val kitchenRoom= Room(RoomType.KITCHEN,1)
        val loungeRoom= Room(RoomType.LOUNGE,1)

        lista.add(kitchenRoom)
        lista.add(loungeRoom)
        emit(lista)
    }*/

    val rooms: Flow<List<Room>>
        get() = repository.roomsList
}