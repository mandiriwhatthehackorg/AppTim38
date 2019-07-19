package id.co.mandiri.corelibrary.sharedpreferences

import android.content.SharedPreferences

/**
 * Created by pertadima on 19,July,2019
 */


class PreferenceManager(private val sharedPreferences: SharedPreferences) {
    companion object {
        const val PREFERENCE_LIVEDATA_KEY = "POSITION"
    }
    val selectedPosition = PreferenceLiveData(sharedPreferences, PREFERENCE_LIVEDATA_KEY, 0)
}