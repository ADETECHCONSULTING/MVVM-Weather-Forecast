package fr.atraore.weather_forecast.data.repository

import androidx.lifecycle.LiveData
import fr.atraore.weather_forecast.data.db.CurrentWeatherDao
import fr.atraore.weather_forecast.data.db.WeatherLocationDao
import fr.atraore.weather_forecast.data.db.entity.CurrentWeatherEntry
import fr.atraore.weather_forecast.data.db.entity.Location
import fr.atraore.weather_forecast.data.network.WeatherNetworkDataSource
import fr.atraore.weather_forecast.data.network.response.CurrentWeatherResponse
import fr.atraore.weather_forecast.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
  private val currentWeatherDao: CurrentWeatherDao,
  private val weatherLocationDao: WeatherLocationDao,
  private val weatherNetworkDataSource: WeatherNetworkDataSource,
  private val locationProvider: LocationProvider
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

  override suspend fun getWeatherLocation(): LiveData<Location> {
    return withContext(Dispatchers.IO) {
      return@withContext weatherLocationDao.getLocation()
    }
  }

  private fun persistCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
    GlobalScope.launch(Dispatchers.IO) {
      currentWeatherDao.updateOrInsert(fetchedWeather.currentWeatherEntry)
      weatherLocationDao.updateOrInsert(fetchedWeather.location)
    }
  }

  private suspend fun initWeatherData() {
    val lastWeatherLocation = weatherLocationDao.getLocation().value

    if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
      fetchCurrentWeather()
      return
    }

    if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime)) {
      fetchCurrentWeather()
    }
  }

  private suspend fun fetchCurrentWeather() {
    weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString())
  }

  private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
    val thirthyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
    return lastFetchTime.isBefore(thirthyMinutesAgo)
  }
}
