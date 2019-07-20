package id.co.mandiri.milenials_deposit.data.mandiri
import com.google.gson.annotations.SerializedName


/**
 * Created by pertadima on 19,July,2019
 */

data class JwtResponse(
    @SerializedName("jwt")
    val jwt: String?
)