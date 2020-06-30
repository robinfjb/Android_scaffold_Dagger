package robin.scaffold.dagger.repo

import robin.scaffold.dagger.db.AppDatabase
import robin.scaffold.dagger.db.Book
import robin.scaffold.dagger.db.Shop
import robin.scaffold.dagger.utils.ListTypeConverters
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

    suspend fun deleteBookById(id:Int) {
        val books = db.bookDao().loadAllByIds(intArrayOf(id))
        books?.apply {
            if(books.isEmpty().not()) {
                db.bookDao().delete(books[0])
            }
        }
    }

    suspend fun queryAll(): String? {
        val books = db.bookDao().getAll()
        val strs = books.map {
            it.toString()
        }
        return ListTypeConverters.strListToString(strs)
    }

    suspend fun queryByFilter(name:String, priceLowest:Int, priceHighest:Int) :String?{
        val books = db.bookDao().findByFilter(name, priceLowest, priceHighest)
        val strs = books.map {
            it.toString()
        }
        return ListTypeConverters.strListToString(strs)
    }
}