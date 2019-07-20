package id.co.mandiri.milenials_deposit.section.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import id.co.mandiri.milenials_deposit.base.BaseViewModel
import id.co.mandiri.milenials_deposit.data.firebase.SavingAuthModel
import javax.inject.Inject

/**
 * Created by pertadima on 20,July,2019
 */

class HistoryLifePlanViewModel @Inject constructor() : BaseViewModel() {
    private var databaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    private val isErrorFirebase = MutableLiveData<String>()
    fun observeErrorFirebase(): LiveData<String?> = isErrorFirebase

    private val savingInformation = MutableLiveData<List<SavingAuthModel>>()
    fun observeSavingInformation(): LiveData<List<SavingAuthModel>> = savingInformation

    fun getSavingInformation() {
        isLoading.postValue(true)
        val listSavingInformaiton = mutableListOf<SavingAuthModel>()

        databaseFirestore.collection("saving")
            .get()
            .addOnSuccessListener { documentReference ->
                isLoading.postValue(false)
                listSavingInformaiton.clear()
                documentReference.forEach { dataQuery ->
                    listSavingInformaiton.add(
                        SavingAuthModel(
                            "${dataQuery.data["name"]}",
                            "${dataQuery.data["duedate"]}",
                            "${dataQuery.data["nominal"]}".toDouble(),
                            "${dataQuery.data["duration"]}".toDouble(),
                            "${dataQuery.data["done"]}".toInt()
                        )
                    )
                    savingInformation.postValue(listSavingInformaiton)
                }
            }
            .addOnFailureListener { e ->
                isLoading.postValue(false)
                isErrorFirebase.postValue(e.localizedMessage)
            }
    }
}
