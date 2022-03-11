package angelini.domotica.ui.settings

import android.content.Context
import android.os.Bundle
import android.preference.ListPreference
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import angelini.domotica.MainApplication
import angelini.domotica.R
import angelini.domotica.databinding.FragmentSettingsBinding
import angelini.domotica.ui.RepositoryViewModelFactory
import angelini.domotica.utility.*

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var viewModelFactory: RepositoryViewModelFactory
    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: FragmentSettingsBinding

    /*
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = RepositoryViewModelFactory((activity?.application as MainApplication).getRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SettingsViewModel::class.java)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textSettings.text = it
        }
        return binding.root
    }*/


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_layout, rootKey)

        val sharedPreferences =
            requireContext().getSharedPreferences(PREF_DB_NAME, Context.MODE_PRIVATE)
        val language = sharedPreferences.get(PREF_TITLE_LANG, LANGUAGE_DEFAULT)
        val theme = sharedPreferences.get(PREF_TITLE_THEME, THEME_DEFAULT)

        val languagePreferences: ListPreference? = findPreference(PREF_TITLE_LANG)
        languagePreferences?.let {
            initLangPrefVal(language, it)
            it.setOnPreferenceChangeListener { _, newValue ->
                handleChangeLanguage(newValue.toString())
                true
            }
        }
        val themePreferences: ListPreference? = findPreference(PREF_TITLE_THEME)
        themePreferences?.let {
            initThemePrefVal(it, theme)
            it.setOnPreferenceChangeListener { _, newValue ->
                handleThemeSwitch(newValue.toString())
                true
            }
        }
    }

    private fun initThemePrefVal(it: ListPreference, theme: String) {
        val array = requireContext().resources.getStringArray(R.array.theme_array)
        it.value = when (theme) {
            THEME_DEFAULT -> array[0]
            else -> array[1]
        }
    }

    private fun initLangPrefVal(language: String, it: ListPreference) {
        val array = requireContext().resources.getStringArray(R.array.language_array)
        val langCode = when (language) {
            LANGUAGE_PL -> array[0]
            LANGUAGE_EN -> array[1]
            LANGUAGE_ES -> array[2]
            LANGUAGE_DE -> array[3]
            else -> array[4]
        }
        it.value = langCode.toString()
    }

    private fun handleThemeSwitch(newTheme: String) {
        val array = requireContext().resources.getStringArray(R.array.theme_array)
        val theme = when (newTheme) {
            array[0] -> THEME_DEFAULT
            else -> THEME_NIGHT
        }
        requireContext().getSharedPreferences(PREF_DB_NAME, Context.MODE_PRIVATE).apply {
            put(PREF_TITLE_THEME, theme)
        }
        requireActivity().recreate()
    }

    private fun handleChangeLanguage(newLang: String) {
        val array = requireContext().resources.getStringArray(R.array.language_array)
        val langCode = when (newLang) {
            array[0] -> LANGUAGE_PL
            array[1] -> LANGUAGE_EN
            array[2] -> LANGUAGE_ES
            array[3] -> LANGUAGE_DE
            else -> LANGUAGE_CS
        }
        requireContext()
            .getSharedPreferences(PREF_DB_NAME, Context.MODE_PRIVATE).apply {
                put(PREF_TITLE_LANG, langCode)
            }
        requireActivity().recreate()
    }

    companion object {
        const val LANGUAGE_PL = "pl"
        const val LANGUAGE_EN = "en"
        const val LANGUAGE_ES = "es"
        const val LANGUAGE_DE = "de"
        const val LANGUAGE_CS = "cs"
        const val THEME_NIGHT = "night"
    }
}