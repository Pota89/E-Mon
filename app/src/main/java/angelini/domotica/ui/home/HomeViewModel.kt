package angelini.domotica.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeViewModel(private val repository: Repository): ViewModel(){
    val rooms: Flow<List<Room>>
        get() = repository.roomsList
}