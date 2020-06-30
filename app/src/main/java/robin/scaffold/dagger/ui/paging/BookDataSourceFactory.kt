package robin.scaffold.dagger.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import robin.scaffold.dagger.db.Book

class BookDataSourceFactory: DataSource.Factory<Int, Book>() {
    val sourceLiveData = MutableLiveData<ItemKeyedBookDataSource>()
    override fun create(): DataSource<Int, Book> {
        val source = ItemKeyedBookDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}