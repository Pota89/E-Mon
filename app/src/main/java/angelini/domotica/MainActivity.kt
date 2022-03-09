package angelini.domotica

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_login, R.id.nav_home), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //implement custom behavior for Logout item entry
        navView.menu.findItem(R.id.nav_login).setOnMenuItemClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                (applicationContext as MainApplication).getRepository().disconnect()
            }
            Toast.makeText(applicationContext, "Logout", Toast.LENGTH_LONG).show()
            navController.navigate(R.id.nav_login)
            drawerLayout.closeDrawers()
            true
        }

        navController.addOnDestinationChangedListener(listener)

        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //listener for dynamic drawer menu
    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.nav_login -> {
                navView.menu.findItem(R.id.nav_home).isVisible = false
                navView.menu.findItem(R.id.nav_login).isVisible = false
            }
            R.id.nav_home -> {
                navView.menu.findItem(R.id.nav_home).isVisible = true
                navView.menu.findItem(R.id.nav_login).isVisible = true
            }
        }
    }
}