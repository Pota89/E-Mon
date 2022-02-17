package angelini.domotica.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import angelini.domotica.repository.Repository
import angelini.domotica.ui.home.HomeViewModel
import angelini.domotica.ui.login.LoginViewModel
import angelini.domotica.ui.room.RoomViewModel
import angelini.domotica.ui.settings.SettingsViewModel

/**
 * Implementazione custom della classe ViewModelProvider.Factory
 *
 * Tale implementazione è necessaria affinchè il sistema oeprativo fornisca una e una sola
 * istanza per ciascun ViewModel per tutta l'esecuzione dell'applicazione includendo la dipendenza
 * dal Repository
 *
 */
class RepositoryViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}