package angelini.domotica.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import angelini.domotica.MainApplication
import angelini.domotica.R
import angelini.domotica.databinding.FragmentLoginBinding
import angelini.domotica.repository.MQTT_PWD
import angelini.domotica.repository.MQTT_USERNAME
import angelini.domotica.ui.RepositoryViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var viewModelFactory: RepositoryViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        viewModelFactory = RepositoryViewModelFactory((activity?.application as MainApplication).getRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(LoginViewModel::class.java)
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonConnect.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val loginResult=viewModel.login(binding.edittextUsername.text.toString(), binding.edittextPassword.text.toString())
                if(loginResult) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        val navController = findNavController()
                        navController.navigate(R.id.nav_home)
                    }
                }
                else
                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonPrefill.setOnClickListener {
            binding.edittextUsername.setText(MQTT_USERNAME)
            binding.edittextPassword.setText(MQTT_PWD)
        }
        /*
        viewModel.text.observe(viewLifecycleOwner) {
            //TODO update bindings for login fragment
            //binding.textLogin.text = it
        }*/
        return binding.root
    }
}