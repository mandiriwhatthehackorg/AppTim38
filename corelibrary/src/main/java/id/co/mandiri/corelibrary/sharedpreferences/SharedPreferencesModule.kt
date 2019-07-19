package id.co.mandiri.corelibrary.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by pertadima on 19,July,2019
 */

@Module
open class SharedPreferencesModule(
    private val sharedPreferencesName: String,
    private val context: Context
) {

    @Provides
    @Singleton
    fun providesSharedPreferences(): SharedPreferences =
        context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesSharedPreferencesHelper(sharedPreferences: SharedPreferences): SharedPreferenceHelper =
        SharedPreferenceHelper(sharedPreferences)


    @Provides
    @Singleton
    fun providessharedPreferenceLiveData(sharedPreference: SharedPreferences) =
        PreferenceManager(sharedPreference)
}