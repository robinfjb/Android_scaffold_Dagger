package robin.scaffold.dagger.utils

import android.app.Activity
import android.app.Service
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


inline fun <reified T: Activity> Context.startActivity(vararg params: Pair<String, Any?>) =
        Internals.internalStartActivity(this, T::class.java, params)

inline fun <reified T: Activity> Activity.startActivityForResult(requestCode: Int, vararg params: Pair<String, Any?>) =
        Internals.internalStartActivityForResult(this, T::class.java, requestCode, params)

inline fun <reified T: Service> Context.startService(vararg params: Pair<String, Any?>) =
        Internals.internalStartService(this, T::class.java, params)

fun coroutine(block: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch { block() }
}

fun coroutineMain(block: suspend () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch { block() }
}

fun String.utc2Local(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = Date(toLong())
    return simpleDateFormat.format(date)
}