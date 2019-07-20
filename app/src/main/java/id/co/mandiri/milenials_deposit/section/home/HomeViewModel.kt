package id.co.mandiri.milenials_deposit.section.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import id.co.mandiri.milenials_deposit.base.BaseViewModel
import id.co.mandiri.milenials_deposit.data.firebase.DebitInformation
import javax.inject.Inject

/**
 * Created by pertadima on 20,July,2019
 */

class HomeViewModel @Inject constructor() : BaseViewModel() {
    companion object {
        private const val TABLE_DEBIT = "debit"
        private const val USERNAME_FIELD = "username"
    }

    private var databaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val isErrorFirebase = MutableLiveData<String>()
    fun observeErrorFirebase(): LiveData<String?> = isErrorFirebase

    private val debitInformation = MutableLiveData<List<DebitInformation>>()
    fun observeDebitInformation(): LiveData<List<DebitInformation>> = debitInformation

    fun getUserAccountInformation(username: String) {
        isLoading.postValue(true)
        val listDebitInformation = mutableListOf<DebitInformation>()
        listDebitInformation.clear()

        databaseFirestore.collection(TABLE_DEBIT)
            .whereEqualTo(USERNAME_FIELD, username)
            .get()
            .addOnSuccessListener { documentReference ->
                isLoading.postValue(false)
                documentReference.forEach { dataQuery ->
                    listDebitInformation.add(
                        DebitInformation(
                            "${dataQuery.data["username"]}",
                            "${dataQuery.data["cif_number"]}",
                            "${dataQuery.data["account_number"]}"
                        )
                    )

                    debitInformation.postValue(listDebitInformation)
                }
            }
            .addOnFailureListener { e ->
                isLoading.postValue(false)

                Log.e("IRFAN", "${e.localizedMessage}: ");
            }
    }
}