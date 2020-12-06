package fr.atraore.weather_forecast.data.provider

import android.content.Context
import fr.atraore.weather_forecast.data.db.entity.Location

const val CUSTOM_LOCATION = "CUSTOM_LOCATION"
class LocationProviderImpl(context: Context) : PreferenceProvider(context), LocationProvider {
  override suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean {
    return hasCustomLocationChanged(lastWeatherLocation)
  }

  override suspend fun getPreferredLocationString(): String {
    return "${getCustomLocationName()}"
  }

  private fun hasCustomLocationChanged(lastWeatherLocation: Location): Boolean {
    val customLocationName = getCustomLocationName()
    return customLocationName != lastWeatherLocation.name
  }

  private fun getCustomLocationName(): String? {
    return preferences.getString(CUSTOM_LOCATION, null)
  }
}
