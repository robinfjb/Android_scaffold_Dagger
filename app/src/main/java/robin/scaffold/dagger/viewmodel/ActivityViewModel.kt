package robin.scaffold.dagger.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import robin.scaffold.dagger.component.SingleLiveEvent
import javax.inject.Inject


class ActivityViewModel@Inject constructor(application: Application): AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _fabClickLivaData = SingleLiveEvent<Any>()
    val fabClickLivaData: LiveData<Any>
        get() = _fabClickLivaData

    fun fabClick() {
        _fabClickLivaData.call()
    }
}