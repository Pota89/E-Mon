package angelini.domotica.ui.login

import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository

/**
 * Classe intermedia tra la View e il Repository.
 *
 * Eventuali manipolazioni di logica dati tra Repository e LoginFragment
 * vanno effettuate in questa classe
 *
 * @property repository repository fornito da RepositoryViewModelFactory
 */
class LoginViewModel(private val repository: Repository): ViewModel(){

    /**
     * Effettua una richiesta di login al Repository
     *
     * @property username username del server MQTT del repository
     * @property password password del server MQTT del repository
     */
    suspend fun login(username:String,password:String):Boolean{
        return repository.connect(username,password)
    }
}