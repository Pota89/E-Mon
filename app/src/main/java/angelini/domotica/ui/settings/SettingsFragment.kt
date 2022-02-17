package angelini.domotica.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import angelini.domotica.MainApplication
import angelini.domotica.databinding.FragmentSettingsBinding
import angelini.domotica.ui.RepositoryViewModelFactory

class SettingsFragment : Fragment() {
    private lateinit var viewModelFactory: RepositoryViewModelFactory
    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = RepositoryViewModelFactory((activity?.application as MainApplication).getRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SettingsViewModel::class.java)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textSettings.text = it
        }
        return binding.root
    }
}