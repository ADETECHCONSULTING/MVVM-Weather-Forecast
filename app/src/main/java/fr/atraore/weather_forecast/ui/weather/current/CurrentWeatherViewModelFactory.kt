package fr.atraore.weather_forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.atraore.weather_forecast.data.repository.ForecastRepository

class CurrentWeatherViewModelFactory(
  private val forecastRepository: ForecastRepository
) : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return CurrentWeatherViewModel(forecastRepository) as T
  }
}
