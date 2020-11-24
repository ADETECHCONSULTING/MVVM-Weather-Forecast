package fr.atraore.weather_forecast.data.network.response

import com.google.gson.annotations.SerializedName
import fr.atraore.weather_forecast.data.db.entity.CurrentWeatherEntry
import fr.atraore.weather_forecast.data.db.entity.Location
import fr.atraore.weather_forecast.data.db.entity.Request


data class CurrentWeatherResponse(
  @SerializedName("current")
  val currentWeatherEntry: CurrentWeatherEntry,
  val location: Location,
  val request: Request
)
