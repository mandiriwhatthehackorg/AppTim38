package id.co.mandiri.milenials_deposit.deps.module

import android.content.Context
import dagger.Module
import dagger.Provides
import id.co.mandiri.corelibrary.commons.DiffCallback
import id.co.mandiri.corelibrary.network.ConnectionLiveData
import id.co.mandiri.milenials_deposit.networking.NetworkAdapter
import id.co.mandiri.milenials_deposit.networking.NetworkService
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by pertadima on 19,July,2019
 */

@Module
open class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun providesDiffCallback() = DiffCallback()

    @Provides
    @Singleton
    fun provideConnectionLiveData() = ConnectionLiveData(context)


    @Provides
    @Singleton
    fun providesNetworkServices(retrofit: Retrofit): NetworkService = retrofit.create(NetworkService::class.java)

    @Provides
    @Singleton
    fun providesNetworkManager(networkService: NetworkService) = NetworkAdapter(networkService)
}