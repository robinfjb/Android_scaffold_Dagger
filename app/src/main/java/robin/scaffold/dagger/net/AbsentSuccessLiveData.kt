package robin.scaffold.dagger.net

import androidx.lifecycle.LiveData

class AbsentSuccessLiveData<T : Any?> private constructor(message: String) : LiveData<Resource<T>>() {
    init {
        // use post instead of set since this can be created on any thread
        postValue(Resource<T>(Status.SUCCESS, null, message))
    }

    companion object {
        fun <T> create(message: String): LiveData<Resource<T>> {
            return AbsentSuccessLiveData(message)
        }
    }
}