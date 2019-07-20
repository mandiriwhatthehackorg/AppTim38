package id.co.mandiri.milenials_deposit.data.mandiri
import com.google.gson.annotations.SerializedName


/**
 * Created by pertadima on 19,July,2019
 */

data class AccountInformation(
    @SerializedName("Response")
    val response: Response?
)

data class Response(
    @SerializedName("Exception")
    val exception: Exception?,
    @SerializedName("cif")
    val cif: CifModel?
)

