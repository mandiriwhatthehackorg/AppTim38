package id.co.mandiri.milenials_deposit.section.login

import android.os.Bundle
import com.jakewharton.rxbinding2.widget.RxTextView
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import id.co.mandiri.milenials_deposit.utils.setAvailable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        observeEditTextChanges()
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
}
