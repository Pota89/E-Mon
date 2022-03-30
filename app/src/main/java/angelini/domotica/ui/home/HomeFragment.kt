package angelini.domotica.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import angelini.domotica.MainApplication
import angelini.domotica.R
import angelini.domotica.databinding.FragmentHomeBinding
import angelini.domotica.ui.RepositoryViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Classe per la visualizzazione della pagina Home
 *
 * Riguarda la schermata principale del programma, recupera le informazioni
 * da HomeViewModel e mostra la lista delle stanze disponibili
 * Alla selezione della stanza da parte dell'utente deve permettere il movimento
 * verso il Fragment di dettaglio (RoomFragment)
 *
 */
class HomeFragment : Fragment() {
    private lateinit var viewModelFactory: RepositoryViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    /**
     * Esegue le inizializzazioni prima di creare la schermata
     *
     * Metodo ereditato da Fragment e richiamato automaticamente dal sistema operativo
     * prima di creare la View per la prima volta.
     * Effettua le operazioni necessarie per collegare il ViewModel con la View.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelFactory = RepositoryViewModelFactory((activity?.application as MainApplication).getRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = RoomAdapter()
        binding.roomList.adapter = adapter

        lifecycle.coroutineScope.launch {
            viewModel.rooms.collect {
                adapter.submitList(it)
            }
        }

        return binding.root
    }

    /**
     * Verifica la corretta connessione al repository, altrimenti torna al login
     *
     * Metodo ereditato da Fragment e richiamato automaticamente dal sistema operativo
     * dopo aver creato la View per la prima volta.
     * Vengono effettuate le operazioni che necessitano di avere la View inflated
     * (intesa come generata a partire dal file XML),nel caso specifico, il metodo
     * "findNavController()" richiede una View inflated per funzionare.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        if(!viewModel.isConnected()){
            Log.i("Login", "Not authenticated, go to login")
            navController.navigate(R.id.nav_login)
        }
        else
            Log.i("Login", "Authenticated")
    }
}