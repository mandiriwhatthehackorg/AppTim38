package id.co.mandiri.milenials_deposit.section.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import id.co.mandiri.corelibrary.commons.SliderPagerAdapter
import id.co.mandiri.corelibrary.sharedpreferences.SharedPreferenceHelper
import id.co.mandiri.corelibrary.viewutils.ZoomOutPageTransformer
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import id.co.mandiri.milenials_deposit.data.OnboardingModel
import id.co.mandiri.milenials_deposit.section.home.HomeActivity
import id.co.mandiri.milenials_deposit.section.login.LoginActivity
import id.co.mandiri.milenials_deposit.section.login.LoginActivity.Companion.IS_LOGGED_IN
import kotlinx.android.synthetic.main.activity_onboarding.*
import javax.inject.Inject

class OnboardingActivity : BaseActivity(), ViewPager.OnPageChangeListener {
    companion object {
        const val NUM_PAGES = 3
    }

    private val listOnboarding by lazy {
        listOf(
            OnboardingModel(
                R.drawable.ic_launcher_foreground,
                getString(R.string.text_dummy_onboarding_title),
                getString(R.string.text_dummy_onboarding_desc)
            ),
            OnboardingModel(
                R.drawable.ic_launcher_foreground,
                getString(R.string.text_dummy_onboarding_title),
                getString(R.string.text_dummy_onboarding_desc)
            ),
            OnboardingModel(
                R.drawable.ic_launcher_foreground,
                getString(R.string.text_dummy_onboarding_title),
                getString(R.string.text_dummy_onboarding_desc)
            )
        )
    }

    private lateinit var indicators: ArrayList<ImageView>
    private lateinit var fragmentList: ArrayList<Fragment>

    private lateinit var sliderAdapter: SliderPagerAdapter


    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_onboarding)
        if (sharedPreferenceHelper.getBoolean(IS_LOGGED_IN)) {
            startActivity(Intent(this@OnboardingActivity, HomeActivity::class.java))
            finish()
        }
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        initViewPager()
        setupPagerIndicator(NUM_PAGES)
        btn_next.setOnClickListener {
            startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun initViewPager() {
        fragmentList = ArrayList()
        listOnboarding.forEach {
            fragmentList.add(OnboardingFragment.newInstance(it.image, it.title, it.content))
            indicators = ArrayList(NUM_PAGES)
        }

        sliderAdapter = SliderPagerAdapter(supportFragmentManager, fragmentList)


        with(pager_slide) {
            adapter = sliderAdapter
            addOnPageChangeListener(this@OnboardingActivity)
            setPageTransformer(true, ZoomOutPageTransformer())
        }
    }

    private fun setupPagerIndicator(size: Int) {

        for (i in 0 until size) {
            indicators.add(ImageView(this@OnboardingActivity))
            indicators[i].setImageDrawable(
                ContextCompat.getDrawable(
                    this@OnboardingActivity,
                    if (i == 0) R.drawable.dot_indicator else R.drawable.dot_indicator_disabled
                )
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }

            val margin = resources.getDimensionPixelSize(R.dimen.dimen_indicator)
            params.setMargins(
                if (i != 0) margin else 0,
                0,
                margin,
                0
            )
            layout_indicator.addView(indicators[i], params)
        }
        indicators.first().isSelected = true
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        pager_slide.currentItem = position

        for (i in 0 until NUM_PAGES) {
            indicators[i].setImageDrawable(
                ContextCompat.getDrawable(
                    this@OnboardingActivity,
                    R.drawable.dot_indicator_disabled
                )
            )
        }
        indicators[position].setImageDrawable(
            ContextCompat.getDrawable(
                this@OnboardingActivity,
                R.drawable.dot_indicator
            )
        )

        with(btn_next) {
            if (NUM_PAGES == position + 1) {
                alpha = 0.0F
                scaleX = 0.0F
                scaleY = 0.0F
                animate()
                    .alpha(1.0F)
                    .scaleX(1.0F).scaleY(1.0F)
                    .setDuration(300)
                    .start()
                isInvisible = false
            } else {
                alpha = 0.1F
                scaleX = 0.1F
                scaleY = 0.1F
                animate()
                    .alpha(0.0F)
                    .scaleX(0.0F).scaleY(0.0F)
                    .setDuration(300)
                    .start()
                isInvisible = true
            }
        }
    }
}
