package robin.scaffold.dagger.di.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import robin.scaffold.dagger.BuildConfig
import robin.scaffold.dagger.db.AppDatabase
import robin.scaffold.dagger.net.ApiLibService
import robin.scaffold.dagger.net.LiveDataCallAdapterFactory
import robin.scaffold.dagger.net.NullOnEmptyConverterFactory
import robin.scaffold.dagger.net.interceptor.BasicParamsInterceptor
import robin.scaffold.dagger.net.interceptor.GzipRequestInterceptor
import robin.scaffold.dagger.net.interceptor.MyHttpLoggingInterceptor
import robin.scaffold.dagger.repo.PreferenceObject
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object {
        const val API_BASE_URL = "http://www.weather.com.cn"
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient, gson: Gson): ApiLibService {
        return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(ApiLibService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(context: Context,
                                     db: AppDatabase,
                                     obj: PreferenceObject
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(GzipRequestInterceptor())
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(BasicParamsInterceptor.Builder(context)
                    .addParam("from", "android") //添加公共参数到 post 请求体
                    .addQueryParam("version","1")  // 添加公共版本号，加在 URL 后面
                    .addHeaderLine("Client-Info: app-1.1.0")  // 示例： 添加公共消息头
                    .build())
            addInterceptor(MyHttpLoggingInterceptor(db, obj))
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(StethoInterceptor())
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }
        return httpClientBuilder.build()
    }
}