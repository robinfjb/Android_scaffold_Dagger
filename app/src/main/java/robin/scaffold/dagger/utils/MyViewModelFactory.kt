package robin.scaffold.dagger.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class MyViewModelFactory @Inject constructor(
        /**
         * 这就是由@IntoMap产生的Map<Key, Provider<Value>>
         * Provider表示对象并不在inject的时候就实例化，或者当使用get（）呼叫时才实例化，下一次呼叫get（）就再实例化另一个
         * 因为Map就是由Dagger产生的，所以同为Dagger管理的GithubViewModelFactory当然可以直接将其作为参数获取。
         */
        private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class " + modelClass)
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}