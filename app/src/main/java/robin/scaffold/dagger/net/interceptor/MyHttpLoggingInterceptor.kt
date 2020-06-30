package robin.scaffold.dagger.net.interceptor

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okio.Buffer
import okio.GzipSource
import robin.scaffold.dagger.db.AppDatabase
import robin.scaffold.dagger.db.LogData
import robin.scaffold.dagger.repo.PreferenceObject
import robin.scaffold.dagger.utils.coroutine
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MyHttpLoggingInterceptor @Inject constructor(
   private val db: AppDatabase,
   private val obj: PreferenceObject
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val requestBody = request.body

        val connection = chain.connection()
        val requestStartMessage =
            ("--> ${request.method} ${request.url}${if (connection != null) " " + connection.protocol() else ""}")

        val logList = mutableListOf<String>()

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            logList.add("<-- HTTP FAILED: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body!!
        val contentLength = responseBody.contentLength()

        val shouldLog = response.code < 200 || response.code >= 400

        if(shouldLog) {

            when {
                requestBody == null -> {
                    logList.add("requestBody == null")
                }
                bodyHasUnknownEncoding(request.headers) -> {
                    logList.add("--> END bodyHasUnknownEncoding ${request.method} (encoded body omitted)")
                }
                requestBody.isDuplex() -> {
                    logList.add("--> END ${request.method} (duplex request body omitted)")
                }
                else -> {
                    val buffer = Buffer()
                    requestBody.writeTo(buffer)
                    val contentType = requestBody.contentType()
                    val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

                    when {
                        "gzip".equals(request.headers["Content-Encoding"], ignoreCase = true) -> GzipSource(buffer.clone()).use { gzippedResponseBody ->
                            buffer.clear()
                            buffer.writeAll(gzippedResponseBody)
                            logList.add(buffer.clone().readString(charset))
                        }
                        buffer.isProbablyUtf8() -> {
                            logList.add(buffer.readString(charset))
                            logList.add("--> END ${request.method} (${requestBody.contentLength()}-byte body)")
                        }
                        else -> logList.add(
                            "--> END ${request.method} (binary ${requestBody.contentLength()}-byte body omitted)")
                    }
                }
            }

            logList.add(requestStartMessage)
            val  requestLog =   "${response.code}${if (response.message.isEmpty()) "" else ' ' + response.message} ${response.request.url} (${tookMs})"
            logList.add(requestLog)

            val headers = response.headers
            when {
                !response.promisesBody() -> logList.add("<-- END HTTP")
                bodyHasUnknownEncoding(response.headers) -> logList.add("<-- END HTTP (encoded body omitted)")
                else -> {
                    val source = responseBody.source()
                    source.request(Long.MAX_VALUE) // Buffer the entire body.
                    var buffer = source.buffer
                    if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                        GzipSource(buffer.clone()).use { gzippedResponseBody ->
                            buffer = Buffer()
                            buffer.writeAll(gzippedResponseBody)
                        }
                    }
                    val contentType = responseBody.contentType()
                    val charset: Charset =
                        contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

                    if (!buffer.isProbablyUtf8()) {
                        logList.add("<-- END HTTP (binary ${buffer.size}-byte body omitted)")
                        return response
                    }

                    if (contentLength != 0L) {
                        logList.add(buffer.clone().readString(charset))
                    }
                }
            }
        }

        coroutine {
            if(logList.isNotEmpty() && requestStartMessage.contains("elogs").not()) {
                db.logDao().insertLogData(LogData(System.currentTimeMillis(),  "exceptions", logList.toString()))
            }
        }

        return response
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
            !contentEncoding.equals("gzip", ignoreCase = true)
    }

    private fun Buffer.isProbablyUtf8(): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = size.coerceAtMost(64)
            copyTo(prefix, 0, byteCount)
            for (i in 0 until 16) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (_: EOFException) {
            return false // Truncated UTF-8 sequence.
        }
    }
}