package robin.scaffold.dagger.repo

import androidx.lifecycle.*
import robin.scaffold.dagger.db.AppDatabase
import robin.scaffold.dagger.db.Book
import robin.scaffold.dagger.db.Shop
import robin.scaffold.dagger.utils.ListTypeConverters
import robin.scaffold.dagger.utils.coroutine
import javax.inject.Inject

class RoomRepository@Inject constructor(
        val db: AppDatabase
) {

    suspend fun insertBook(vararg data:Book) {
        db.bookDao().insertBook(*data)
    }

    suspend fun insertShop(vararg data:Shop) {
        db.bookDao().insertShop(*data)
    }

    fun loadAllByIds(ids : IntArray) : LiveData<List<Book>> = db.bookDao().loadAllByIds(ids)


    suspend fun deleteBook(book :Book) = db.bookDao().delete(book)

    fun queryAll() = db.bookDao().getAll()

    fun queryByFilter(name:String, priceLowest:Int, priceHighest:Int) = db.bookDao().findByFilter(name, priceLowest, priceHighest)
}