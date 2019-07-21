package id.co.mandiri.milenials_deposit.deps.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.co.mandiri.milenials_deposit.section.addlifeplan.CreateLifePlanActivity
import id.co.mandiri.milenials_deposit.section.history.HistoryLifePlanActivity
import id.co.mandiri.milenials_deposit.section.home.HomeActivity
import id.co.mandiri.milenials_deposit.section.login.LoginActivity
import id.co.mandiri.milenials_deposit.section.onboarding.OnboardingActivity
import id.co.mandiri.milenials_deposit.section.profile.ProfileActivity
import id.co.mandiri.milenials_deposit.section.verification.*
import id.co.mandiri.milenials_deposit.section.webrtc.WebRtcActivity

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

    @ContributesAndroidInjector
    abstract fun verificationIntroductionActivity(): VerificationIntroductionActivity

    @ContributesAndroidInjector
    abstract fun historyLifePlanActivity(): HistoryLifePlanActivity

    @ContributesAndroidInjector
    abstract fun verificationKtpActivity(): VerificationKtpActivity

    @ContributesAndroidInjector
    abstract fun verificationSelfieActivity(): VerificationSelfieActivity

    @ContributesAndroidInjector
    abstract fun verificationSignatureActivity(): VerificationSignatureActivity

    @ContributesAndroidInjector
    abstract fun webRtcAcivity(): WebRtcActivity

    @ContributesAndroidInjector
    abstract fun transActionSuccessActivity(): TransactionSuccessActivity
}