package id.co.mandiri.milenials_deposit.base

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.android.support.DaggerAppCompatActivity
import id.co.mandiri.corelibrary.network.ConnectionLiveData
import id.co.mandiri.milenials_deposit.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by pertadima on 19,July,2019
 */

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    private val disposables = CompositeDisposable()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseActivity, Observer { data -> data?.let(action) })
    }

    protected fun boundNetwork(action: (Boolean) -> Unit = {}) {
        connectionLiveData.onResult {
            action.invoke(it)
        }
    }

    protected fun Disposable.track() {
        disposables.add(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
            val decorView = window.decorView //set status background black
            decorView.systemUiVisibility =
                decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        onSetupLayout(savedInstanceState)
        onViewReady(savedInstanceState)
    }

    fun setupToolbarProperties(
        toolbarId: Toolbar,
        tvTitle: TextView? = null,
        @StringRes title: Int = R.string.empty_string
    ) {
        setSupportActionBar(toolbarId)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tvTitle?.setText(title)
    }

    fun setupToolbarPropertiesWithBackButton(
        toolbarId: Toolbar,
        tvTitle: TextView? = null,
        @StringRes title: Int = R.string.empty_string,
        @DrawableRes drawable: Int? = R.drawable.ic_arrow_back
    ) {
        setSupportActionBar(toolbarId)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(
                null != drawable
            )
            it.setDisplayShowHomeEnabled(null != drawable)
            drawable?.let { iconUp ->
                it.setHomeAsUpIndicator(iconUp)
            }
        }
        tvTitle?.setText(title)
    }

    fun String.changeToolbarTitle(tvTitle: TextView? = null) {
        tvTitle?.text = this
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected abstract fun onSetupLayout(savedInstanceState: Bundle?)
    protected abstract fun onViewReady(savedInstanceState: Bundle?)

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}