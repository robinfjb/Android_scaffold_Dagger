package robin.scaffold.dagger.repo

import io.reactivex.Single
import robin.scaffold.dagger.model.WeatherResult
import robin.scaffold.dagger.net.SdkNetApi
import javax.inject.Inject

class HomeRepository @Inject constructor(){
    private val sdkNetApi : SdkNetApi by lazy(LazyThreadSafetyMode.NONE) {
        SdkNetApi()
    }

    fun getWeather() : Single<WeatherResult> {
        return sdkNetApi.getWeather()
    }
}