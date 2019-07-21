package id.co.mandiri.milenials_deposit.data.mandiri

import com.google.gson.annotations.SerializedName

/**
 * Created by pertadima on 20,July,2019
 */

data class Exception(
    @SerializedName("status")
    val status: String?
)