package id.co.mandiri.milenials_deposit.deps

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import id.co.mandiri.corelibrary.network.NetworkModule
import id.co.mandiri.corelibrary.sharedpreferences.SharedPreferencesModule
import id.co.mandiri.milenials_deposit.MainApp
import id.co.mandiri.milenials_deposit.deps.builder.ActivityBuilder
import id.co.mandiri.milenials_deposit.deps.module.AppModule
import javax.inject.Singleton

/**
 * Created by pertadima on 19,July,2019
 */

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        AppModule::class,
        ActivityBuilder::class,
        SharedPreferencesModule::class
    ]
)

interface AppComponent : AndroidInjector<MainApp> {
    fun inject(instance: DaggerApplication)
    override fun inject(application: MainApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun networkModule(networkModule: NetworkModule): Builder
        fun appModule(appModule: AppModule): Builder
        fun sharePreferenceModule(sharedPreferenceModule: SharedPreferencesModule) : Builder
        fun build(): AppComponent
    }
}