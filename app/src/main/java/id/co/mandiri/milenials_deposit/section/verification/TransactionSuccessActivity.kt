package id.co.mandiri.milenials_deposit.section.verification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import id.co.mandiri.milenials_deposit.section.home.HomeActivity
import kotlinx.android.synthetic.main.activity_transaction_success.*
import kotlinx.android.synthetic.main.default_toolbar.view.*

class TransactionSuccessActivity : BaseActivity() {
    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_transaction_success)
        setupToolbarPropertiesWithBackButton(
            toolbar as Toolbar,
            toolbar.toolbar_title,
            R.string.empty_string
        )
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        btn_add_life_plan.setOnClickListener {
            startActivity(Intent(this@TransactionSuccessActivity, HomeActivity::class.java))
            finishAffinity()
        }
    }
}
