package robin.scaffold.dagger

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import robin.scaffold.dagger.di.AppInjectorHelper
import javax.inject.Inject

class SampleApp : Application(), HasAndroidInjector, LifecycleObserver {
    companion object {
        private lateinit var instance: SampleApp
        var isAppInForeground = false
        fun getAppContext(): Context =
                instance.applicationContext
    }

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun onCreate() {
        super.onCreate()
        AppInjectorHelper.inject(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector

    /**
     * Callback when the app is open but backgrounded
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        isAppInForeground = false
    }

    /**
     * Callback when the app is foregrounded
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        isAppInForeground = true
    }
}