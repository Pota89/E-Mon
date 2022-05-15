package angelini.domotica.ui.room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import angelini.domotica.MainApplication
import angelini.domotica.databinding.FragmentRoomBinding
import angelini.domotica.ui.RepositoryViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Classe per la visualizzazione della pagina Room
 *
 * E' la schermata di dettaglio della singla Room e mostra i Device ivi presenti.
 *
 * Riguarda la schermata principale del programma, recupera le informazioni
 * da HomeViewModel e mostra la lista delle stanze disponibili
 * Alla selezione della stanza da parte dell'utente deve permettere il movimento
 * verso il Fragment di dettaglio (RoomFragment)
 *
 */
class RoomFragment : Fragment() {
    private lateinit var viewModelFactory: RepositoryViewModelFactory
    private lateinit var viewModel: RoomViewModel
    private lateinit var binding: FragmentRoomBinding
    private val args: RoomFragmentArgs by navArgs()

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
            .get(RoomViewModel::class.java)
        binding = FragmentRoomBinding.inflate(inflater, container, false)

        val adapter = DeviceAdapter{device->
            lifecycle.coroutineScope.launch(Dispatchers.IO) {
                viewModel.update(device)
            }
        }

        binding.deviceList.adapter = adapter

        lifecycle.coroutineScope.launch(Dispatchers.Main) {
            viewModel.getRoomDevices(args.roomType,args.roomNumber).collect {
                adapter.submitList(it)
            }
        }

        return binding.root
    }
}