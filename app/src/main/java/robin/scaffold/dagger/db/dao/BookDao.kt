package robin.scaffold.dagger.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import robin.scaffold.dagger.db.Book
import robin.scaffold.dagger.db.BookWithShop
import robin.scaffold.dagger.db.Shop

@Dao
interface BookDao {
//    @Query("SELECT * FROM book")
//    fun getAll(): List<Book>

    @Query("SELECT * FROM book WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): LiveData<List<Book>>

    @Query("SELECT * FROM book WHERE name LIKE :name AND price BETWEEN :priceLowest AND :priceHighest")
    fun findByFilter(name: String, priceLowest:Int, priceHighest:Int): LiveData<List<Book>>

    @Delete
    suspend fun delete(user: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(vararg data: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShop(vararg data: Shop)

    @Transaction
    @Query("SELECT * FROM shop")
    fun getAll(): LiveData<List<BookWithShop>>
}