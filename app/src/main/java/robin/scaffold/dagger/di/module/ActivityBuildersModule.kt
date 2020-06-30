package robin.scaffold.dagger.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import robin.scaffold.dagger.ui.MainActivity
import robin.scaffold.dagger.ui.NavTestActivity


/**
 * 在此Module中指定哪些Activity/Fragment要用Inject的方式取得物件
 */
@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [NavTestActivityModule::class])
    abstract fun contributeNavTestActivity(): NavTestActivity
}