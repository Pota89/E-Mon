package angelini.domotica.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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