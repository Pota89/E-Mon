package angelini.domotica.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import angelini.domotica.repository.Repository

/**
 * Implementazione custom della classe ViewModelProvider.Factory
 *
 * Tale implementazione è necessaria affinchè il sistema oeprativo fornisca una e una sola
 * istanza della HomeViewModel per tutta l'esecuzione dell'applicazione includendo la dipendenza
 * dal Repository
 *
 */
class HomeViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}