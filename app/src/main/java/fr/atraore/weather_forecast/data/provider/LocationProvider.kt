package fr.atraore.weather_forecast.data.provider

import fr.atraore.weather_forecast.data.db.entity.Location

interface LocationProvider {
  suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean
  suspend fun getPreferredLocationString(): String
}
