package robin.scaffold.dagger.utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import robin.scaffold.dagger.net.*
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

fun <T> LiveData<ApiResponse<T>>.asResource(): LiveData<Resource<T>> {
    return Transformations.map(this) { response ->
        when (response) {
            is ApiSuccessResponse<T> -> {
                Resource.success(response.body)
            }
            is ApiEmptyResponse<T> -> {
                Resource.error("empty", null)
            }
            is ApiErrorResponse<T> -> {
                Resource.error(response.errorMessage, null)
            }
            else -> {
                Resource.error("", null)
            }
        }
    }
}

fun <T> LiveData<ApiResponse<T>>.asResourceCompactEmpty(): LiveData<Resource<T>> {
    return Transformations.map(this) { response ->
        when (response) {
            is ApiSuccessResponse<T> -> {
                Resource.success(response.body)
            }
            is ApiEmptyResponse<T> -> {
                Resource.success(null)
            }
            is ApiErrorResponse<T> -> {
                Resource.error(response.errorMessage, null)
            }
            else -> {
                Resource.error("", null)
            }
        }
    }
}

fun isNetworkConnected(context: Context): Boolean {
    if (context != null) {
        try {
            val connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager?.activeNetworkInfo
            return networkInfo != null && networkInfo.isAvailable
        } catch (ignored: Exception) {
        }
    }
    return true
}