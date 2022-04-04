package angelini.domotica.ui.room

import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import kotlinx.coroutines.flow.Flow

/**
 * Classe intermedia tra la View e il Repository.
 *
 * Eventuali manipolazioni di logica dati tra Repository e RoomFragment
 * vanno effettuate in questa classe
 *
 * @property repository repository fornito da RepositoryViewModelFactory
 */
class RoomViewModel(private val repository: Repository): ViewModel() {
    /**
     * Restituisce un Flow con i dispositivi presenti nella Room d'interesse
     *
     * L'impiego del Flow permette di aggiornare automaticamente sia i dispositivi
     * presenti nella Room che il loro status
     *
     * @property roomType tipologia di Room d'interesse
     * @property roomNumber numero della Room all'interno della tipologia
     */
    fun getRoomDevices(roomType: RoomType, roomNumber: Int): Flow<List<Device>> {
        return repository.getRoomDevicesList(Room(roomType,roomNumber))
    }

    /**
     * Chiede di aggiornare un Device dall'App
     *
     * Viene effettuata una richiesta MQTT al server remoto per aggiornare il Device
     * d'interesse. L'aggiornamento all'interno dell'App viene effettuato separatamente
     * tramite la ricezione della notifica dell'aggiornaento da remoto.
     *
     * @property device Device da aggiornare sul server MQTT
     */
    suspend fun update(device: Device){
        repository.update(device)
    }
}