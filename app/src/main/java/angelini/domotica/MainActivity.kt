package angelini.domotica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import angelini.domotica.data.NetworkClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val networkClient=NetworkClient(applicationContext)
        networkClient.connect(applicationContext)
        val result=networkClient.isConnected().toString()
        val toast = Toast.makeText(applicationContext, result, Toast.LENGTH_LONG)
        toast.show()
        //networkClient.disconnect()
    }
}