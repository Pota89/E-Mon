package angelini.domotica.ui.home

import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.Room
import kotlinx.coroutines.flow.Flow

/**
 * Classe intermedia tra la View e il Repository.
 *
 * Eventuali manipolazioni di logica dati tra Repository e View
 * vanno effettuate in questa classe
 */
class HomeViewModel(private val repository: Repository): ViewModel(){
    val rooms: Flow<List<Room>>
        get() = repository.roomsList

    /**
     * Interroga se il Repository è connesso e i dati disponibili
     */
    fun isConnected():Boolean{
        return repository.isConnected()
    }
}