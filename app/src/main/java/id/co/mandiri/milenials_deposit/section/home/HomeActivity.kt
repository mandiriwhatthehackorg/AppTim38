package id.co.mandiri.milenials_deposit.section.home

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*





class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var progressStatus = 0
    private val handler = Handler()

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.empty_string)
        setSupportActionBar(toolbar)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        initDrawerLayout()
        myAccountInformation()
        animateProgressBar()
    }

    private fun myAccountInformation() {
        tv_account_name.text = getString(R.string.text_my_name, "Irfan Pertadima")
        tv_account_number.text = getString(R.string.text_my_account_number, "1234567897")
    }

    private fun animateProgressBar() {
        Thread(Runnable {
            while (progressStatus < 50) {
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

    private fun initDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
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
                // Handle the camera action
            }
            R.id.nav_lifeplan -> {

            }
            R.id.nav_history_lifeplan -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
