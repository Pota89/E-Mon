package angelini.domotica

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*
import java.util.*

/**
 * Activity che ospita i diversi Fragment dell'applicativo
 *
 * Il suo scopo è quello di fornire uno scheletro all'interfaccia e fornire il supporto
 * alla navigazione, in particolare alla navigazione condizionale del login
 */
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView

    /**
     * Inizializza l'activity impostando la logica di navigazione
     */
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

    /**
     * Funzionalità di navigazione nella Topbar
     *
     * Fornisce la possibilità di navigare all'indietro se in un Fragment secondario.
     * Se in un fragment top level come Login o Home, permette l'apertura del drawer
     */
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

    /**
     * Sovrascrive alcune informazioni del context fornite dal sistema operativo
     *
     * Fornisce opzionalmente una lingua e un tema diversi da quelli di default
     * del sistema operativo.
     * Il metodo viene chiamato ogni volta che l'app viene avviata, compresi
     * i riavvi su richiesta dell'app stessa
     *
     * @property baseContext context fornito normalmente dal sistema operativo
     */
    override fun attachBaseContext(baseContext: Context) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext)

        var systemLanguage=Locale.getDefault().language
        if(systemLanguage!="en" && systemLanguage!="it") //force english if system language is not supported
            systemLanguage="en"

        val locale= when (sharedPreferences.getString(PREF_TITLE_LANG, LANGUAGE_DEFAULT)!!) {
            "system" -> Locale(systemLanguage)
            "en" -> Locale("en")
            "it" ->Locale("it")
            else ->Locale(systemLanguage)
        }

        val configuration: Configuration = baseContext.resources.configuration
        configuration.setLocale(locale)
        val newContext=baseContext.createConfigurationContext(configuration)
        when (sharedPreferences.getString(PREF_TITLE_THEME, THEME_DEFAULT)!!) {
            THEME_DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            THEME_DAY -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_NIGHT ->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else ->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.attachBaseContext(newContext)
    }
}