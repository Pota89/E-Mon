package angelini.domotica.data

import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Repository(context:Context){

    private val networkClient:NetworkClient = NetworkClient(context)

    private val _roomList = mutableListOf<Room>()
    val roomList: List<Room>
        get() = _roomList

    fun connect() {
        networkClient.connect()
    }

    fun disconnect() {
        networkClient.disconnect()
    }
}