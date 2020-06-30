package robin.scaffold.dagger.repo

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import robin.scaffold.dagger.db.Book
import robin.scaffold.dagger.ui.paging.Listing
import robin.scaffold.dagger.ui.paging.BookDataSourceFactory
import javax.inject.Inject

class PagingRepository @Inject constructor() {
    fun getBookList(pageSize: Int): Listing<Book> {
        val sourceFactory= BookDataSourceFactory()
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize)//定义第一页加载项目的数量
                .setPageSize(pageSize)//定义从DataSource中每一次加载的项目数量
                .build()
        val pagedList = LivePagedListBuilder(sourceFactory, pagedListConfig).build()
        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        val networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.networkState
        }
        return Listing<Book>(
                pagedList = pagedList,
                networkState = networkState,
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }
}