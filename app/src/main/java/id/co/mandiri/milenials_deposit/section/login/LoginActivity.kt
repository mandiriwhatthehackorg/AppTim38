package id.co.mandiri.milenials_deposit.section.login

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.jakewharton.rxbinding2.widget.RxTextView
import id.co.mandiri.corelibrary.commons.showToast
import id.co.mandiri.corelibrary.sharedpreferences.SharedPreferenceHelper
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import id.co.mandiri.milenials_deposit.section.home.HomeActivity
import id.co.mandiri.milenials_deposit.utils.setAvailable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    companion object {
        const val IS_LOGGED_IN = "isLoggedIn"
        const val USERNAME = "username"
    }
    @Inject
    lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        observeEditTextChanges()
        observeViewModel()
    }

    private fun observeEditTextChanges() {
        Observable.zip(
            RxTextView.textChanges(et_username)
                .map { it.isNotEmpty() },
            RxTextView.textChanges(et_password)
                .map { it.isNotEmpty() },
            BiFunction { user: Boolean, pass: Boolean ->
                user && pass
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                btn_login.setAvailable(it, this)
            }.track()
    }

    private fun observeViewModel() {
        with(loginViewModel) {

            observeLoading().onResult {
                btn_login.isInvisible = it
                progress_bar.isVisible = it
            }

            observeErrorLogin().onResult {
                showToast(it.toString())
            }

            observeLoginSuccess().onResult {
                it?.let {data ->
                    sharedPreferenceHelper.setBoolean(IS_LOGGED_IN, data.isSuccess)
                    sharedPreferenceHelper.setString(USERNAME, data.username.toString())
                }

                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            }
            boundNetwork {
                if (it) {
                    btn_login.setOnClickListener {
                        loginViewModel.doLogin(et_username.text.toString(), et_password.text.toString())
                    }
                } else {
                    showToast(getString(R.string.text_no_internet))
                }
            }
        }
    }
}
