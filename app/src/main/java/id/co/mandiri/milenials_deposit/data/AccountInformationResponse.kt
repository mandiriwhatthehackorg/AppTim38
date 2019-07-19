package id.co.mandiri.milenials_deposit.data
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
    val cif: Cif?
)

data class Exception(
    @SerializedName("status")
    val status: String?
)

data class Cif(
    @SerializedName("CIFNumber")
    val cIFNumber: String?,
    @SerializedName("branchNumber")
    val branchNumber: String?,
    @SerializedName("bankNumber")
    val bankNumber: String?,
    @SerializedName("CIFName1")
    val cIFName1: String?,
    @SerializedName("CIFName2")
    val cIFName2: String?,
    @SerializedName("titleAfterName")
    val titleAfterName: String?,
    @SerializedName("address1")
    val address1: String?,
    @SerializedName("address2")
    val address2: String?,
    @SerializedName("address3")
    val address3: String?,
    @SerializedName("address4")
    val address4: String?,
    @SerializedName("postalCode")
    val postalCode: String?,
    @SerializedName("telephone")
    val telephone: String?,
    @SerializedName("fax")
    val fax: String?,
    @SerializedName("handphone")
    val handphone: String?,
    @SerializedName("customerTypeCode")
    val customerTypeCode: Any?,
    @SerializedName("residentCode")
    val residentCode: String?,
    @SerializedName("occupationCode")
    val occupationCode: String?,
    @SerializedName("IDTypeCode")
    val iDTypeCode: String?,
    @SerializedName("IDNumber")
    val iDNumber: String?,
    @SerializedName("IDExpiryDate")
    val iDExpiryDate: Any?,
    @SerializedName("IDIssuePlace")
    val iDIssuePlace: String?,
    @SerializedName("IDStatusCode")
    val iDStatusCode: Any?,
    @SerializedName("dayOfBirth")
    val dayOfBirth: String?,
    @SerializedName("birthIncorporationPlace")
    val birthIncorporationPlace: String?,
    @SerializedName("sexCode")
    val sexCode: String?,
    @SerializedName("countryOfCitizenship")
    val countryOfCitizenship: String?,
    @SerializedName("motherMaidenName")
    val motherMaidenName: String?,
    @SerializedName("businessUnitCode")
    val businessUnitCode: String?,
    @SerializedName("codeField1")
    val codeField1: String?,
    @SerializedName("codeFlag1")
    val codeFlag1: String?,
    @SerializedName("codeAmount1")
    val codeAmount1: String?,
    @SerializedName("codeCurrency1")
    val codeCurrency1: String?,
    @SerializedName("codeField2")
    val codeField2: String?,
    @SerializedName("codeFlag2")
    val codeFlag2: String?,
    @SerializedName("codeAmount2")
    val codeAmount2: String?,
    @SerializedName("codeCurrency2")
    val codeCurrency2: String?,
    @SerializedName("codeAmount3")
    val codeAmount3: String?
)