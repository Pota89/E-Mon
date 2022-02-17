package angelini.domotica.ui.home

import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository
import angelini.domotica.repository.datatypes.Room
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val repository: Repository): ViewModel(){
    val rooms: Flow<List<Room>>
        get() = repository.roomsList
}