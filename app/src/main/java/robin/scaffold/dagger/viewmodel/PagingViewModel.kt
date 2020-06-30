package robin.scaffold.dagger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import robin.scaffold.dagger.repo.PagingRepository
import javax.inject.Inject

class PagingViewModel@Inject constructor(
        private val repository: PagingRepository
) : ViewModel() {
    private val mText: MutableLiveData<String> = MutableLiveData()
    val text: LiveData<String>
        get() = mText

    private val data = MutableLiveData<String>()
    private val repoResult = Transformations.map(data) {
        repository.getBookList(20)
    }

    val posts = Transformations.switchMap(repoResult) { it.pagedList }!!
    val networkState = Transformations.switchMap(repoResult) { it.networkState }!!
    val refreshState = Transformations.switchMap(repoResult) { it.refreshState }!!

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult?.value
        listing?.retry?.invoke()
    }

    fun invoke() {
        data.value = ""
    }
}