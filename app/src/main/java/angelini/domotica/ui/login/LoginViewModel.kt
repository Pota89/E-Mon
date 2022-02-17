package angelini.domotica.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository

class LoginViewModel(private val repository: Repository): ViewModel(){

    private val _text = MutableLiveData<String>().apply {
        value = "This is login Fragment"
    }
    val text: LiveData<String> = _text
}