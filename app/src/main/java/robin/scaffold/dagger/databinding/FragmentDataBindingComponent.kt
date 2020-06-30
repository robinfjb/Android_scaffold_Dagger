package robin.scaffold.dagger.databinding

import androidx.databinding.DataBindingComponent

class FragmentDataBindingComponent() : DataBindingComponent {
    private val bindingAdapter = BindingAdapters
    private val swipeRefreshLayoutBinding = SwipeRefreshLayoutBinding

    override fun getBindingAdapters() = bindingAdapter
}