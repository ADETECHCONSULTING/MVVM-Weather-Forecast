package fr.atraore.weather_forecast.data.db.entity


import androidx.room.*
import com.google.gson.annotations.SerializedName
import fr.atraore.weather_forecast.data.internals.WeatherTypeConverters

const val CURRENT_WEATHER_ID = 0


@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("observation_time")
    val observationTime: String,
    val precip: Int,
    val pressure: Int,
    @SerializedName("feelslike")
    val feelsLike: Int,
    val temperature: Int,
    val visibility: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Int
) {
  @PrimaryKey(autoGenerate = false)
  var id: Int = CURRENT_WEATHER_ID

}
