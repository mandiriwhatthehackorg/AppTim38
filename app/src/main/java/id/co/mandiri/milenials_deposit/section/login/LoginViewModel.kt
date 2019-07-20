package id.co.mandiri.milenials_deposit.section.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import id.co.mandiri.milenials_deposit.base.BaseViewModel
import id.co.mandiri.milenials_deposit.data.firebase.FirebaseAuthModel
import javax.inject.Inject

/**
 * Created by pertadima on 20,July,2019
 */

class LoginViewModel @Inject constructor() : BaseViewModel() {
    companion object {
        private const val TABLE_NASABAH = "nasabah"
        private const val USERNAME_FIELD = "username"
        private const val PASSWORD_FIELD = "password"
    }

    private val isSuccessLogin = MutableLiveData<FirebaseAuthModel>()
    fun observeLoginSuccess(): LiveData<FirebaseAuthModel?> = isSuccessLogin

    private val isErrorLogin = MutableLiveData<String>()
    fun observeErrorLogin(): LiveData<String?> = isErrorLogin

    private var databaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun doLogin(username: String, password: String) {
        isLoading.postValue(true)

        databaseFirestore.collection(TABLE_NASABAH)
            .whereEqualTo(USERNAME_FIELD, username)
            .whereEqualTo(PASSWORD_FIELD, password)
            .get()
            .addOnSuccessListener { documentReference ->
                isLoading.postValue(false)
                if (documentReference.isEmpty) {
                    isErrorLogin.postValue("Maaf, login gagal. Pastikan username dan password kamu benar")
                } else {
                    documentReference.forEach { data ->
                        isSuccessLogin.postValue(
                            FirebaseAuthModel(
                                true,
                                "${data.data["username"]}"
                            )
                        )
                    }
                }
            }
            .addOnFailureListener { e ->
                isLoading.postValue(false)
                isErrorLogin.postValue(e.localizedMessage)
            }
    }


}
