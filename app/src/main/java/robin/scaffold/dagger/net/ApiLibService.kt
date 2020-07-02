package robin.scaffold.dagger.net

import androidx.lifecycle.LiveData
import io.reactivex.Single
import retrofit2.http.*
import robin.scaffold.dagger.model.WeatherResult


interface ApiLibService {
    @Headers("Content-Type: application/json", "Content-Encoding: gzip")
    @GET("/data/cityinfo/101010100.html")
    fun weather(): LiveData<ApiResponse<WeatherResult>>
}