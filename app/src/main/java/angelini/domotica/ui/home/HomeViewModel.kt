package angelini.domotica.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import angelini.domotica.repository.datatypes.Room

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    //private val _rooms = getApplication<MainApplication>().getRepository().roomsList

    //TODO remove repository detach code
    private lateinit var _rooms:LiveData<List<Room>>
    init {
        val list: List<Room> = emptyList()
        fun <T : Any?> MutableLiveData<T>.default(initialValue: T) =
            apply { setValue(initialValue) }
        _rooms= MutableLiveData<List<Room>>().default(list)
    }


    val rooms: LiveData<List<Room>>
        get() = _rooms
}