package angelini.domotica.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import angelini.domotica.R

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
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_login, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = root.findViewById(R.id.text_login)
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
    }
}