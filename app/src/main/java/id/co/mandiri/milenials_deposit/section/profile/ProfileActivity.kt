package id.co.mandiri.milenials_deposit.section.profile

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import id.co.mandiri.corelibrary.commons.showToast
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import id.co.mandiri.milenials_deposit.section.home.HomeActivity.Companion.ACCOUNT_NUMBER_TAG
import id.co.mandiri.milenials_deposit.section.home.HomeActivity.Companion.CIF_NUMBER_TAG
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.default_toolbar.view.*
import javax.inject.Inject

class ProfileActivity : BaseActivity() {
    @Inject
    lateinit var profileViewModel: ProfileViewModel

    private var cifNumber = ""
    private var accountNumber = ""
    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_profile)
        setupToolbarPropertiesWithBackButton(
            toolbar as Toolbar,
            toolbar.toolbar_title,
            R.string.empty_string
        )
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        cifNumber = intent.getStringExtra(CIF_NUMBER_TAG)
        accountNumber = intent.getStringExtra(ACCOUNT_NUMBER_TAG)
        observeViewModel()
    }

    private fun observeViewModel() {
        with(profileViewModel) {
            observeLoading().onResult {
                linearLayout.isVisible = !it
                progress_bar.isVisible = it
            }

            observeLoadProfileData().onResult {
                tv_name_profile.text = getString(R.string.text_name, "${it?.cIFName1} ${it?.cIFName2}")
                tv_no_hp.text = getString(R.string.text_phone, it?.handphone)
                tv_alamat.text = getString(
                    R.string.text_address,
                    "${it?.address1}, ${it?.address2}, ${it?.address3}, ${it?.address4} ${it?.postalCode}"
                )
                tv_account_number.text = accountNumber
            }

            observeError().onResult {
                it?.let { error ->
                    showToast(error.errorMessage.toString())
                }
            }

            boundNetwork {
                if (it) {
                    profileViewModel.fetchProfile(cifNumber)
                } else {
                    showToast(getString(R.string.text_no_internet))
                }
            }
        }
    }
}