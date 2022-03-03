package angelini.domotica.ui.login

import androidx.lifecycle.ViewModel
import angelini.domotica.repository.Repository

class LoginViewModel(private val repository: Repository): ViewModel(){

    suspend fun login(username:String,password:String):Boolean{
        return repository.connect(username,password)
    }
}