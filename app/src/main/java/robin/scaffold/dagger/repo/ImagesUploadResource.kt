package robin.scaffold.dagger.repo

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import robin.scaffold.dagger.model.ImageModel
import robin.scaffold.dagger.model.WeatherInfo
import robin.scaffold.dagger.net.ApiErrorResponse
import robin.scaffold.dagger.net.ApiResponse
import robin.scaffold.dagger.net.ApiSuccessResponse
import robin.scaffold.dagger.net.Resource
import robin.scaffold.dagger.utils.coroutine
import robin.scaffold.dagger.utils.coroutineMain


abstract class ImagesUploadResource @MainThread constructor() {

    private val result = MediatorLiveData<Resource<Any>>()
    private val dataList = mutableListOf<ImageModel>()

    init {
        @Suppress("LeakingThis")
        val pointsDb = getPointsFromDb()
        result.addSource(pointsDb) {
            result.removeSource(pointsDb)
            if(it.isNullOrEmpty()) {
                result.value = Resource.success("")
            } else {
                uploadImages(it)

            }
        }
    }

    private fun uploadImages(points: List<WeatherInfo>) {
        val size = points.size
        points.filter{
            it.img1.isNotEmpty()
        }.forEach { point->
            coroutine {
                val stream = createStream(point.img1)
                coroutineMain {
                    uploadImage(stream, point, size)
                }
            }
        }
    }

    private fun uploadImage(stream: ByteArray, point: WeatherInfo, size: Int) {
        val apiResponse = createCall(point, stream)
        result.addSource(apiResponse) {
                response->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse<ImageModel> -> {
                    coroutine {
                        notifyPointDataChanged(point)
                        val model = ImageModel(response.body.url)
                        dataList.add(model)
                        if(dataList.size == size) {
                            result.postValue(Resource.success(""))
                        }
                    }
                }
                is ApiErrorResponse<ImageModel> -> result.value = Resource.error(response.errorMessage, null)
                else -> result.value = Resource.error("", null)
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<Any>>

    @WorkerThread
    protected abstract fun createStream(imagePath: String): ByteArray

    @MainThread
    protected abstract fun getPointsFromDb(): LiveData<List<WeatherInfo>>

    @WorkerThread
    protected abstract fun notifyPointDataChanged(point: WeatherInfo)

    @MainThread
    protected abstract fun createCall(point: WeatherInfo, stream: ByteArray): LiveData<ApiResponse<ImageModel>>
}