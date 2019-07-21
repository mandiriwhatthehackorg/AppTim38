package id.co.mandiri.milenials_deposit.section.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.co.mandiri.milenials_deposit.base.BaseViewModel
import id.co.mandiri.milenials_deposit.data.mandiri.CifModel
import id.co.mandiri.milenials_deposit.networking.NetworkAdapter
import javax.inject.Inject

/**
 * Created by pertadima on 20,July,2019
 */

class ProfileViewModel @Inject constructor(private val networkAdapter: NetworkAdapter) : BaseViewModel() {
    private val loadProfileData = MutableLiveData<CifModel>()
    fun observeLoadProfileData(): LiveData<CifModel?> = loadProfileData

    fun fetchProfile(cifNumber: String) {
        networkAdapter.getJwtToken().onResult({
            getProfileInformation("Bearer ${it.jwt}", cifNumber)
        }, {
            isError.postValue(it)
        })
    }

    private fun getProfileInformation(token: String, cifNumber: String) {
        networkAdapter.getProfileInformation(token, cifNumber).onResult({
            loadProfileData.postValue(it.response?.cif)
        }, {
            isError.postValue(it)
        })
    }
}