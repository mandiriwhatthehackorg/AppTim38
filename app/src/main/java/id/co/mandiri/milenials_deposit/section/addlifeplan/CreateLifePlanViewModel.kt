package id.co.mandiri.milenials_deposit.section.addlifeplan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import id.co.mandiri.milenials_deposit.base.BaseViewModel
import id.co.mandiri.milenials_deposit.data.hardcoded.LifePlanPackageModel
import javax.inject.Inject

/**
 * Created by pertadima on 20,July,2019
 */

class CreateLifePlanViewModel @Inject constructor() : BaseViewModel() {
    companion object {
        private const val TABLE_NAME = "package"
    }

    private var databaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val isErrorFirebase = MutableLiveData<String>()
    fun observeErrorFirebase(): LiveData<String?> = isErrorFirebase

    private val lifePlanPackageLiveData = MutableLiveData<List<LifePlanPackageModel>>()
    fun observeLifePlanPackage(): LiveData<List<LifePlanPackageModel>> = lifePlanPackageLiveData

    fun getLifePlanPackage() {
        isLoading.postValue(true)
        val listPlanPackage = mutableListOf<LifePlanPackageModel>()
        listPlanPackage.clear()

        databaseFirestore.collection(TABLE_NAME)
            .get()
            .addOnSuccessListener { documentReference ->
                isLoading.postValue(false)
                documentReference.forEach { dataQuery ->

                    listPlanPackage.add(
                        LifePlanPackageModel(
                            "${dataQuery.data["name"]}",
                            "${dataQuery.data["duration"]}".toDouble(),
                            "${dataQuery.data["nominal"]}".toDouble(),
                            false
                        )
                    )
                    lifePlanPackageLiveData.postValue(listPlanPackage)
                }
            }
            .addOnFailureListener { e ->
                isLoading.postValue(false)
                isErrorFirebase.postValue(e.localizedMessage)
            }
    }

    fun getLifePackage() {

    }

}