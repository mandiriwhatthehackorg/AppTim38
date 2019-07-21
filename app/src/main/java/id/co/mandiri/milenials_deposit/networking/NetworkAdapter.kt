package id.co.mandiri.milenials_deposit.networking

import id.co.mandiri.milenials_deposit.data.mandiri.AccountInformation
import id.co.mandiri.milenials_deposit.data.mandiri.JwtResponse
import io.reactivex.Single
import okhttp3.Credentials
import javax.inject.Inject

/**
 * Created by pertadima on 19,July,2019
 */


class NetworkAdapter @Inject constructor(private val networkService: NetworkService) {
    fun getJwtToken(): Single<JwtResponse> {
        return networkService.getJwtToken(
            Credentials.basic(
                "b035d54c-e6de-4009-833a-c2a594a8e58f",
                "e6cc0c6e-1a45-4804-83b1-d02cf7fda006"
            ), "85128e1a-3e86-4e0f-8c1d-abb73b6f506a"
        )
    }

    fun getProfileInformation(authentication: String, cifNumber: String): Single<AccountInformation> {
        return networkService.getProfileInformation(authentication, cifNumber)
    }
}