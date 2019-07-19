package id.co.mandiri.milenials_deposit

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import id.co.mandiri.corelibrary.network.NetworkModule
import id.co.mandiri.corelibrary.sharedpreferences.SharedPreferencesModule
import id.co.mandiri.milenials_deposit.deps.AppComponent
import id.co.mandiri.milenials_deposit.deps.DaggerAppComponent
import id.co.mandiri.milenials_deposit.deps.module.AppModule

/**
 * Created by pertadima on 19,July,2019
 */


class MainApp : DaggerApplication() {
    companion object {
        const val SHAREDPREFERENCES_NAME = "MandiriWTH2019"
    }
    lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent
            .builder()
            .create(this)
            .networkModule(NetworkModule("https://apigateway.mandiriwhatthehack.com/gateway/"))
            .appModule(AppModule(this))
            .sharePreferenceModule(SharedPreferencesModule(SHAREDPREFERENCES_NAME,this))
            .build()
        appComponent.inject(this)
        return appComponent
    }
}