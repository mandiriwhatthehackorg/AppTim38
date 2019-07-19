package id.co.mandiri.corelibrary.sharedpreferences

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

/**
 * Created by pertadima on 19,July,2019
 */

class PreferenceLiveData<T>(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defValue: T
) : LiveData<T>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == this.key) {
                value = getValueFromPreferences()
            }
        }

    override fun onActive() {
        super.onActive()
        val prefValue = getValueFromPreferences()
        if (value != prefValue) {
            value = prefValue
        }
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        prefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getValueFromPreferences(): T = with(prefs) {
        val res: Any = when (defValue) {
            is String -> getString(key, defValue)
            is Boolean -> getBoolean(key, defValue)
            is Int -> getInt(key, defValue)
            is Float -> getFloat(key, defValue)
            is Long -> getLong(key, defValue)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }
        res as T
    }

    fun update(value: T) = with(prefs.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }.apply()
    }

    fun peek(): T {
        return getValueFromPreferences()
    }
}