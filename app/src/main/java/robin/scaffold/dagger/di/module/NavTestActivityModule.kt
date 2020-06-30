package robin.scaffold.dagger.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import robin.scaffold.dagger.ui.home.HomeFragment
import robin.scaffold.dagger.ui.paging.PagingFragment

@Module
abstract class NavTestActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributePagingFragment(): PagingFragment
}