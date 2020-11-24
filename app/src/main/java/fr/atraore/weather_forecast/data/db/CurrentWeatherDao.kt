package fr.atraore.weather_forecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.atraore.weather_forecast.data.db.entity.CURRENT_WEATHER_ID
import fr.atraore.weather_forecast.data.db.entity.CurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun updateOrInsert(weatherEntry: CurrentWeatherEntry)

  @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
  fun getWeatherMetric(): LiveData<CurrentWeatherEntry>
}
