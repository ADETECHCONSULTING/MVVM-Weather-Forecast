package fr.atraore.weather_forecast

import android.app.Application
import fr.atraore.weather_forecast.data.ApixuWeatherApiService
import fr.atraore.weather_forecast.data.db.CurrentWeatherDao
import fr.atraore.weather_forecast.data.db.ForecastDatabase
import fr.atraore.weather_forecast.data.network.ConnectivityInterceptor
import fr.atraore.weather_forecast.data.network.ConnectivityInterceptorImpl
import fr.atraore.weather_forecast.data.network.WeatherNetworkDataSource
import fr.atraore.weather_forecast.data.network.WeatherNetworkDataSourceImpl
import fr.atraore.weather_forecast.data.repository.ForecastRepository
import fr.atraore.weather_forecast.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


class ForecastApplication : Application(), KodeinAware {
  override val kodein = Kodein.lazy {
    import(androidXModule(this@ForecastApplication))

    bind<ForecastDatabase>() with singleton { ForecastDatabase(instance()) }
    bind<CurrentWeatherDao>() with singleton { instance<ForecastDatabase>().currentWeatherDao() }
    bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) } //interface
    bind<ApixuWeatherApiService>() with singleton { ApixuWeatherApiService(instance()) }
    bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) } //interface
    bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) } //interface
  }
}
