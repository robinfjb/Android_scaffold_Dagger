package robin.scaffold.dagger.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import robin.scaffold.dagger.net.Status
import robin.scaffold.dagger.repo.HomeRepository
import robin.scaffold.dagger.ui.home.HomeFragmentArgs
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val repository: HomeRepository
) : ViewModel(){
    private val _text = MutableLiveData<String>()
    private val _textNet = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val text = _text

    val textNet = _textNet

    fun display() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                postMessage("hello world")
            }
        }
    }

    fun displayArgu(arguments : Bundle?) {
        val msg = arguments?.let { HomeFragmentArgs.fromBundle(it).myArg }
        msg ?.apply {
            postMessage(msg)
        }
    }


    fun getWeather(lifecycleOwner : LifecycleOwner) {
        repository.getWeather().observe(lifecycleOwner, Observer {
            if(it.status == Status.SUCCESS) {
                val data = it.data?.weatherinfo
                _textNet.postValue(data.toString())
            } else {
                _textNet.postValue(it.message)
            }
        })
    }

    private fun postMessage(message: String) {
        val display = "$message".take(10000)
        _text.postValue(display)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel() // Cancel all coroutines
    }
}