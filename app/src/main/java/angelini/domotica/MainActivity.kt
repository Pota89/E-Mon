package angelini.domotica

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import angelini.domotica.data.NetworkClient


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val networkClient=NetworkClient(applicationContext)
        networkClient.connect()

        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                val result=networkClient.isConnected().toString()
                val toast = Toast.makeText(applicationContext, result, Toast.LENGTH_LONG)
                toast.show()
                networkClient.disconnect()
            }
        }.start()
    }
}