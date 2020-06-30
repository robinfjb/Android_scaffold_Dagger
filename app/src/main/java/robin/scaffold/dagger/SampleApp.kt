package robin.scaffold.dagger

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import robin.scaffold.dagger.di.AppInjectorHelper
import javax.inject.Inject

class SampleApp : Application(), HasActivityInjector, LifecycleObserver {
    companion object {
        private lateinit var instance: SampleApp
        var isAppInForeground = false
        fun getAppContext(): Context =
                instance.applicationContext
    }

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        AppInjectorHelper.inject(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

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