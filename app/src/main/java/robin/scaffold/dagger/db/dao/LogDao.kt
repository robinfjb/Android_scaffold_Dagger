package robin.scaffold.dagger.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import robin.scaffold.dagger.db.LogData

@Dao
interface LogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogData(logData: LogData)

    @Query("SELECT * FROM log")
    fun searchLogData(): LiveData<List<LogData>>

    @Query("DELETE FROM log WHERE `time` = :time")
    suspend fun deleteLogByTime(time: Long)
}