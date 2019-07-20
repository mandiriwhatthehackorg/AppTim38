package id.co.mandiri.milenials_deposit.section.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import id.co.mandiri.corelibrary.commons.showToast
import id.co.mandiri.corelibrary.commons.toRupiah
import id.co.mandiri.corelibrary.sharedpreferences.SharedPreferenceHelper
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import id.co.mandiri.milenials_deposit.data.firebase.DebitInformation
import id.co.mandiri.milenials_deposit.data.firebase.SavingAuthModel
import id.co.mandiri.milenials_deposit.section.addlifeplan.CreateLifePlanActivity
import id.co.mandiri.milenials_deposit.section.history.HistoryLifePlanActivity
import id.co.mandiri.milenials_deposit.section.login.LoginActivity
import id.co.mandiri.milenials_deposit.section.login.LoginActivity.Companion.USERNAME
import id.co.mandiri.milenials_deposit.section.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        const val CIF_NUMBER_TAG = "cifNumber"
        const val ACCOUNT_NUMBER_TAG = "accountNumber"
    }

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    private val listDebitInformation = mutableListOf<DebitInformation>()

    private var cifNumber = ""
    private var accountNumber = ""
    private var progressStatus = 0
    private val handler = Handler()

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.empty_string)
        setSupportActionBar(toolbar)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        observeViewModel()
        initDrawerLayout()
        myAccountInformation()

        btn_add_life_plan.setOnClickListener {
            startActivity(Intent(this@HomeActivity, CreateLifePlanActivity::class.java))
        }
    }

    private fun myAccountInformation() {
        tv_account_name.text = "Nomor Rekening"
    }

    private fun initDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun observeViewModel() {
        with(homeViewModel) {
            observeLoading().onResult {
                linearLayout.isInvisible = it
                progress_bar.isVisible = it
            }

            observeLoadingSaving().onResult {
                layout_plan.isInvisible = it
                progress_bar_saving.isVisible = it
            }

            observeErrorFirebase().onResult {
                showToast(it.toString())
            }

            observeDebitInformation().onResult {
                listDebitInformation.addAll(it)
                tv_account_number.text = it.first().accountNumber
                cifNumber = it.first().cifNumber.toString()
                accountNumber = it.first().accountNumber.toString()
            }


            observeSavingInformation().onResult {
                layout_empty.isVisible = it.isEmpty()
                layout_plan.isVisible = it.isNotEmpty()
                tv_life_plan_detail.isVisible = it.isNotEmpty()
                bindSavingView(it.first())
            }


            boundNetwork {
                if (it) {
                    sharedPreferenceHelper.getString(USERNAME)?.let { username ->
                        homeViewModel.getUserAccountInformation(username)
                        homeViewModel.getSavingInformation()
                    }
                } else {
                    showToast(getString(R.string.text_no_internet))
                }
            }
        }
    }

    private fun bindSavingView(data: SavingAuthModel) {
        future_value.text = toRupiah(data.nominal!!)
        current_value.text = toRupiah((data.nominal / data.duration!!) * data.done!!.toDouble())
        saving_value.text = toRupiah(data.nominal / data.duration)
        tv_persentage.text = "${((data.done / data.duration) * 100).toInt()}%"
        tv_plan_for_title.text = data.name

        Thread(Runnable {
            while (progressStatus < ((data.done / data.duration) * 100).toInt()) {
                progressStatus += 1

                try {
                    Thread.sleep(20)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                handler.post {
                    progress.progress = progressStatus
                }
            }
        }).start()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profile -> {
                startActivity(Intent(this@HomeActivity, ProfileActivity::class.java).apply {
                    putExtra(CIF_NUMBER_TAG, cifNumber)
                    putExtra(ACCOUNT_NUMBER_TAG, accountNumber)
                })
            }
            R.id.nav_lifeplan -> {
                startActivity(Intent(this@HomeActivity, CreateLifePlanActivity::class.java))
            }
            R.id.nav_history_lifeplan -> {
                startActivity(Intent(this@HomeActivity, HistoryLifePlanActivity::class.java))
            }
            R.id.action_logout -> {
                sharedPreferenceHelper.clearSharedPreferences()
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                finishAffinity()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
