package robin.scaffold.dagger.net

import androidx.lifecycle.LiveData


class AbsentErrorLiveData<T : Any?> private constructor(message: String) : LiveData<Resource<T>>() {
    init {
        // use post instead of set since this can be created on any thread
        postValue(Resource<T>(Status.ERROR, null, message))
    }

    companion object {
        fun <T> create(message: String): LiveData<Resource<T>> {
            return AbsentErrorLiveData(message)
        }
    }
}