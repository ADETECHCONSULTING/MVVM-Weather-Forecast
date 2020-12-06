package fr.atraore.weather_forecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.atraore.weather_forecast.data.db.entity.Location
import fr.atraore.weather_forecast.data.db.entity.WEATHER_LOCATION_ID

@Dao
interface WeatherLocationDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun updateOrInsert(location: Location)

  @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
  fun getLocation(): LiveData<Location>
}
