package robin.scaffold.dagger.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import robin.scaffold.dagger.SampleApp
import robin.scaffold.dagger.di.module.AppModule
import robin.scaffold.dagger.di.module.ActivityBuildersModule
import robin.scaffold.dagger.di.module.NetworkModule
import robin.scaffold.dagger.di.module.ViewModelModule
import javax.inject.Singleton

/**
 * AndroidSupportInjectionModule是dagger.android協助我們做inject的Module，
 * 如果你不是用support library的話就改成AndroidInjectionModule，
 * 另外兩個就是我們剛剛建立的Module。
 */
@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            ActivityBuildersModule::class
        ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(surveyApp: SampleApp)
}