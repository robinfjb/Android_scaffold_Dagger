package robin.scaffold.dagger.db

import androidx.room.Database
import androidx.room.RoomDatabase
import robin.scaffold.dagger.db.dao.BookDao
import robin.scaffold.dagger.db.dao.LogDao

@Database(entities = [Book::class,Shop::class,LogData::class],
    version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bookDao(): BookDao
    abstract fun logDao(): LogDao
}