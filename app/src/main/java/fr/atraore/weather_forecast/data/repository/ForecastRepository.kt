package fr.atraore.weather_forecast.data.repository

import androidx.lifecycle.LiveData
import fr.atraore.weather_forecast.data.db.CurrentWeatherDao
import fr.atraore.weather_forecast.data.db.entity.CurrentWeatherEntry
import fr.atraore.weather_forecast.data.db.entity.Location
import fr.atraore.weather_forecast.data.network.WeatherNetworkDataSource

interface ForecastRepository {
  suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
  suspend fun getWeatherLocation(): LiveData<Location>
}
