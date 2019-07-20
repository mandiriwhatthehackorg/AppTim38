package id.co.mandiri.milenials_deposit.networking

import retrofit2.http.GET

/**
 * Created by pertadima on 19,July,2019
 */

interface NetworkService {
    @GET("")
    fun getJwtToken()
}