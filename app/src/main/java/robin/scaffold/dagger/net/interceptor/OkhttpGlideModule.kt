package robin.scaffold.dagger.net.interceptor

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import robin.scaffold.dagger.di.DaggerAppComponent
import robin.scaffold.dagger.net.ApiLibService
import java.io.InputStream
import javax.inject.Inject

@GlideModule
class OkhttpGlideModule : AppGlideModule(){
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okHttpClient = OkHttpClient.Builder()
                .eventListenerFactory(PrintingEventListener.FACTORY).build()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }
}