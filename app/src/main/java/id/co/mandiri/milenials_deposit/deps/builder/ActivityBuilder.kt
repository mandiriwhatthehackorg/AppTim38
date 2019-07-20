package id.co.mandiri.milenials_deposit.deps.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.co.mandiri.milenials_deposit.section.login.LoginActivity
import id.co.mandiri.milenials_deposit.section.main.MainActivity
import id.co.mandiri.milenials_deposit.section.onboarding.OnboardingActivity

/**
 * Created by pertadima on 19,July,2019
 */


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindOnboardingActivity(): OnboardingActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity
}