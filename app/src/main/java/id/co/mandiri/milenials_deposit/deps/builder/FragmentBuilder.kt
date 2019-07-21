package id.co.mandiri.milenials_deposit.deps.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.co.mandiri.milenials_deposit.section.onboarding.OnboardingFragment

/**
 * Created by pertadima on 19,July,2019
 */


@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector
    abstract fun bindOnboardingFragment(): OnboardingFragment
}