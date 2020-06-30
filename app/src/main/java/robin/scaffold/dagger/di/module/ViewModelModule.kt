package robin.scaffold.dagger.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import robin.scaffold.dagger.di.ViewModelKey
import robin.scaffold.dagger.utils.MyViewModelFactory
import robin.scaffold.dagger.viewmodel.*


@Module
abstract class ViewModelModule {
    /**
     *@IntoMap会产生一个Map <Key，Provider <Value >>，
     *以此处而言为键是@ViewModelKey的XXViewModel.class，而值为@Binds的参数即XXViewModel
     */
    @Binds//@Binds是@Provides的一种简化的写法，当依赖的建立方式只是用constructor的时候，可以用@Binds来取代@Provides
    @IntoMap
    @ViewModelKey(ActivityViewModel::class)
    abstract fun bindActivityViewModel(activityViewModel: ActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BindViewModel::class)
    abstract fun bindBindViewModel(bindViewModel: BindViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PagingViewModel::class)
    abstract fun bindPagingViewModel(pagingViewModel: PagingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomViewModel::class)
    abstract fun bindRoomViewModel(roomViewModel: RoomViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory
}