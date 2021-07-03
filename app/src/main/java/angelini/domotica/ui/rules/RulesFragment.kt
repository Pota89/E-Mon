package angelini.domotica.ui.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import angelini.domotica.R

class RulesFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rulesViewModel: RulesViewModel by viewModels()

        val root = inflater.inflate(R.layout.fragment_rules, container, false)
        val textView: TextView = root.findViewById(R.id.text_rules)
        rulesViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }
}