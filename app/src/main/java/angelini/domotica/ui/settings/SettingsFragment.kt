package angelini.domotica.ui.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import angelini.domotica.PREF_TITLE_LANG
import angelini.domotica.PREF_TITLE_THEME
import angelini.domotica.R

/**
 * Fragment per selezionare le impostazioni dell'applicazione
 *
 * Questa classe eredita dalla classe di Android PreferenceFragmentCompat, la quale
 * provvede a generare il layout e l'interazione per le impostazioni a partire
 * dal file XML preferences_settings.xml
 */
class SettingsFragment : PreferenceFragmentCompat() {
    /**
     * Effettua l'inflate dall'XML e imposta i relativi listener
     *
     * Metodo ereditato da PreferenceFragmentCompat, effettua l'inflate dall'XML
     * preferences_settings e imposta dei listener per quando vengono cambiate le preferenze.
     * I listener si occupano di riavviare l'applicazione per rendere effettive le
     * preferenze modificate.
     */
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)

        val languagePreference:Preference = findPreference(PREF_TITLE_LANG)!!
        languagePreference.setOnPreferenceChangeListener { _,_ ->
            requireActivity().recreate()
            true
        }

        val themePreference:Preference = findPreference(PREF_TITLE_THEME)!!
        themePreference.setOnPreferenceChangeListener { _,_ ->
            requireActivity().recreate()
            true
        }
    }
}