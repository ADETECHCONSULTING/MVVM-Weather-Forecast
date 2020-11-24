package fr.atraore.weather_forecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.atraore.weather_forecast.data.ApixuWeatherApiService
import fr.atraore.weather_forecast.data.network.response.CurrentWeatherResponse
import java.io.IOException

class WeatherNetworkDataSourceImpl(
  private val apiService: ApixuWeatherApiService
) : WeatherNetworkDataSource {

  private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
  override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    get() = _downloadedCurrentWeather

  override suspend fun fetchCurrentWeather(location: String) {
    try {
      val fetchCurrentWeather = apiService.getCurrentWeather(location).await()
      _downloadedCurrentWeather.postValue(fetchCurrentWeather)
    } catch (e: IOException) {
      Log.e("Connectivity", "No Internet connection", e)
    }
  }
}
