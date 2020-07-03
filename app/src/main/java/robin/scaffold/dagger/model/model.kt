package robin.scaffold.dagger.model

data class FlexModel(val name: String,
                val isTag: Boolean = false,
                val count: Int = 0,
                val content: String = "",
                val extra: String = "")

data class WeatherResult(
        val weatherinfo: WeatherInfo?
)

data class WeatherInfo(
        val city: String,
        val cityid: String,
        val temp1: String,
        val temp2: String,
        val weather: String,
        val img1: String,
        val img2: String,
        val ptime: String
)

data class ImageModel(val url: String)