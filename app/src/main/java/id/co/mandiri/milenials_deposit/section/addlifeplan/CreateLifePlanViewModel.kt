package id.co.mandiri.milenials_deposit.section.addlifeplan

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

    private val isSaveSuccess = MutableLiveData<Boolean?>()
    fun observeSaveSuccess(): LiveData<Boolean?> = isSaveSuccess

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

    fun savePlanPackage(pos: Int) {
        isSaveSuccess.postValue(false)
        val dataPendidikan = hashMapOf(
            "name" to "Pendidikan",
            "duration" to 24,
            "done" to 1,
            "nominal" to 100000000
        )

        val dataPernikahan = hashMapOf(
            "name" to "Pernikahan",
            "duration" to 60,
            "done" to 1,
            "nominal" to 150000000
        )


        val dataMobil = hashMapOf(
            "name" to "Mobil",
            "duration" to 48,
            "done" to 1,
            "nominal" to 100000000
        )

        val dataRumah = hashMapOf(
            "name" to "Rumah",
            "duration" to 76,
            "done" to 1,
            "nominal" to 200000000
        )
        databaseFirestore.collection("saving")
            .document(if (pos == 1) "pendidikan" else if (pos == 2) "pernikahan" else if (pos == 3) "mobil" else "rumah")
            .set(if (pos == 1) dataPendidikan else if (pos == 2) dataPernikahan else if (pos == 3) dataMobil else dataRumah)
            .addOnSuccessListener {
                isSaveSuccess.postValue(true)
            }
            .addOnFailureListener {
                isErrorFirebase.postValue(it.localizedMessage)
            }
    }

}