package angelini.domotica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import angelini.domotica.data.Repository


class MainActivity : AppCompatActivity() {
    private lateinit var repository:Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repository=Repository(applicationContext)
        repository.connect()
    }

    override fun onDestroy() {
        repository.disconnect()
        super.onDestroy()
    }


}