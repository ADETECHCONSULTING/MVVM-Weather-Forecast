package fr.atraore.weather_forecast.data.network

import androidx.lifecycle.LiveData
import fr.atraore.weather_forecast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
  val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

  suspend fun fetchCurrentWeather(
    location: String
  )
}
