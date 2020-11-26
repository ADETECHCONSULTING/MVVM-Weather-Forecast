package fr.atraore.weather_forecast.data.repository

import androidx.lifecycle.LiveData
import fr.atraore.weather_forecast.data.db.CurrentWeatherDao
import fr.atraore.weather_forecast.data.db.entity.CurrentWeatherEntry
import fr.atraore.weather_forecast.data.network.WeatherNetworkDataSource
import fr.atraore.weather_forecast.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
  private val currentWeatherDao: CurrentWeatherDao,
  private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

  init {
      weatherNetworkDataSource.downloadedCurrentWeather.observeForever {newCurrentWeather ->
        //persist
        persistCurrentWeather(newCurrentWeather)
      }
  }
  override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
    initWeatherData()
    return withContext(Dispatchers.IO) {
      return@withContext currentWeatherDao.getWeatherMetric()
    }
  }

  private fun persistCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
    GlobalScope.launch(Dispatchers.IO) {
      currentWeatherDao.updateOrInsert(fetchedWeather.currentWeatherEntry)
    }
  }

  private suspend fun initWeatherData() {
    if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))) {
      fetchCurrentWeather()
    }
  }

  private suspend fun fetchCurrentWeather() {
    weatherNetworkDataSource.fetchCurrentWeather("Paris")
  }

  private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
    val thirthyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
    return lastFetchTime.isBefore(thirthyMinutesAgo)
  }
}
