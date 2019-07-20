package id.co.mandiri.milenials_deposit.networking

import id.co.mandiri.milenials_deposit.data.mandiri.AccountInformation
import id.co.mandiri.milenials_deposit.data.mandiri.JwtResponse
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by pertadima on 19,July,2019
 */

interface NetworkService {
    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/json"]
    )
    @GET("rest/pub/apigateway/jwt/getJsonWebToken")
    fun getJwtToken(@Header("Authorization") authentication: String, @Query("app_id") appId: String): Single<JwtResponse>

    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/json"]
    )
    @GET("gateway/ServicingAPI/1.0/customer/{cif_number}")
    fun getProfileInformation(@Header("Authorization") authentication: String, @Path("cif_number") cifNumber: String): Single<AccountInformation>
}