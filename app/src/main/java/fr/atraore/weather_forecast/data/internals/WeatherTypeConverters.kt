package fr.atraore.weather_forecast.data.internals

import androidx.room.TypeConverter

class WeatherTypeConverters {

  @TypeConverter
  fun stringToListString(list: List<String>): String {
    if (list.isNotEmpty())
      return list[0]
    else
      return ""
  }

}
