package angelini.domotica.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import angelini.domotica.R
import angelini.domotica.ui.login.LoginViewModel

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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeViewModel: HomeViewModel by viewModels()

        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val textView: TextView = root.findViewById(R.id.text_login)
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }
}