package robin.scaffold.dagger.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
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
    private var dispose: Disposable? = null

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


    fun getWeather() {
        dispose = repository.getWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe ({
                    val result = it.weatherinfo
                    _textNet.postValue(result.toString())
                }){
                    _textNet.postValue(it.message)
                }
    }

    private fun postMessage(message: String) {
        val display = "$message".take(10000)
        _text.postValue(display)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel() // Cancel all coroutines
        if(dispose?.isDisposed == false) {
            dispose?.dispose()
        }
    }
}