package id.co.mandiri.milenials_deposit.deps.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.co.mandiri.milenials_deposit.section.addlifeplan.CreateLifePlanActivity
import id.co.mandiri.milenials_deposit.section.home.HomeActivity
import id.co.mandiri.milenials_deposit.section.login.LoginActivity
import id.co.mandiri.milenials_deposit.section.onboarding.OnboardingActivity
import id.co.mandiri.milenials_deposit.section.profile.ProfileActivity

/**
 * Created by pertadima on 19,July,2019
 */


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun bindOnboardingActivity(): OnboardingActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun createLifePlanActivity(): CreateLifePlanActivity

    @ContributesAndroidInjector
    abstract fun profileActivity(): ProfileActivity
}