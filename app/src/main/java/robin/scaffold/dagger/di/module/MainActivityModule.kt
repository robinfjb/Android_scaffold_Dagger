package robin.scaffold.dagger.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import robin.scaffold.dagger.ui.databind.BindFragment
import robin.scaffold.dagger.ui.home.HomeFragment
import robin.scaffold.dagger.ui.room.RoomFragment

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeBindFragment(): BindFragment

    @ContributesAndroidInjector
    abstract fun contributeRoomFragment(): RoomFragment
}