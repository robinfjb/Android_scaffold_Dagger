package robin.scaffold.dagger.repo

import androidx.lifecycle.LiveData
import robin.scaffold.dagger.model.WeatherResult
import robin.scaffold.dagger.net.ApiLibService
import robin.scaffold.dagger.net.Resource
import robin.scaffold.dagger.utils.asResource
import javax.inject.Inject

class HomeRepository @Inject constructor(
        private val apiLibService: ApiLibService
){
    fun getWeather(): LiveData<Resource<WeatherResult>> = apiLibService.weather().asResource()
}