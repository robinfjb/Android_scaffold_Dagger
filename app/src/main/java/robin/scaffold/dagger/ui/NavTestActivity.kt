package robin.scaffold.dagger.ui

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import robin.scaffold.dagger.R
import robin.scaffold.dagger.di.Injectable
import javax.inject.Inject

class NavTestActivity : AppCompatActivity(), Injectable, HasFragmentInjector {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        navController = findNavController(R.id.nav_fragment)
        navController.addOnDestinationChangedListener {
            controller, destination, arguments -> Log.i("addOnNavigatedListener", "label${destination.label}|id:${destination.id}")
        }
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector
}