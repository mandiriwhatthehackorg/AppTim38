package id.co.mandiri.milenials_deposit.deps.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.co.mandiri.milenials_deposit.section.MainActivity

/**
 * Created by pertadima on 19,July,2019
 */


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}