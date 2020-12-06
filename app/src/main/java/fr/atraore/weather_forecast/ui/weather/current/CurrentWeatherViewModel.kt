package fr.atraore.weather_forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import fr.atraore.weather_forecast.data.internals.lazyDeferred
import fr.atraore.weather_forecast.data.repository.ForecastRepository

class CurrentWeatherViewModel(
  private val forecastRepository: ForecastRepository
) : ViewModel() {
  val weather by lazyDeferred {
    forecastRepository.getCurrentWeather()
  }

  val weatherLocation by lazyDeferred {
    forecastRepository.getWeatherLocation()
  }

  fun refreshData() {
    forecastRepository.getCurrentWeather()
  }
}
