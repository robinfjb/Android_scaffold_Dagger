package robin.scaffold.dagger.utils

import android.util.Log
import robin.scaffold.dagger.db.AppDatabase
import robin.scaffold.dagger.db.LogData
import robin.scaffold.dagger.repo.PreferenceObject
import java.io.IOException
import java.lang.Thread.UncaughtExceptionHandler
import javax.inject.Inject

class CrashHandler @Inject constructor(
        private val obj: PreferenceObject,
        private val db: AppDatabase
) : UncaughtExceptionHandler {

    private val mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 这个是最关键的函数，当系统中有未被捕获的异常，系统将会自动调用 uncaughtException 方法
     *
     * @param thread 为出现未捕获异常的线程
     * @param ex     为未捕获的异常 ，可以通过e 拿到异常信息
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        try {
            dumpException(ex)
        } catch (e: IOException) {
            e.printStackTrace()
        }

       val traceText = Log.getStackTraceString(ex)
      //  ex.printStackTrace()
        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        coroutine {
            db.logDao().insertLogData(LogData(System.currentTimeMillis(),  "crash", traceText))

            mDefaultCrashHandler.uncaughtException(thread, ex)
        }
    }

    private fun dumpException(ex: Throwable) {
    }

}

