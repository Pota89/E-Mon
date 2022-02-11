package angelini.domotica.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import angelini.domotica.databinding.FragmentHomeBinding
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
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val adapter = RoomAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.roomList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.coroutineScope.launch {
            viewModel.rooms.collect {
                adapter.submitList(it)
            }
        }
    }
}