package robin.scaffold.dagger.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_home.*
import robin.scaffold.dagger.R
import robin.scaffold.dagger.databinding.FragmentDataBindingComponent
import robin.scaffold.dagger.databinding.FragmentHomeBinding
import robin.scaffold.dagger.databinding.FragmentShareBinding
import robin.scaffold.dagger.databinding.autoCleared
import robin.scaffold.dagger.ui.NavTestActivity
import robin.scaffold.dagger.utils.startActivity
import robin.scaffold.dagger.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var binding by autoCleared<FragmentHomeBinding>()
    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val dataBinding = DataBindingUtil.inflate<FragmentHomeBinding>(
                inflater,
                R.layout.fragment_home,
                container,
                false,
                dataBindingComponent
        )

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            text_home.text = "${text_home.text}\n$it"
        })

        homeViewModel.displayArgu(arguments)

        binding = dataBinding
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return dataBinding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        display_button.setOnClickListener {
            homeViewModel.display()
        }
        go_to_nav.setOnClickListener {
            requireContext().startActivity<NavTestActivity>()
        }
        homeViewModel.getWeather()
    }
}