package fr.atraore.weather_forecast

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import fr.atraore.weather_forecast.data.ApixuWeatherApiService
import fr.atraore.weather_forecast.data.db.CurrentWeatherDao
import fr.atraore.weather_forecast.data.db.ForecastDatabase
import fr.atraore.weather_forecast.data.db.WeatherLocationDao
import fr.atraore.weather_forecast.data.network.ConnectivityInterceptor
import fr.atraore.weather_forecast.data.network.ConnectivityInterceptorImpl
import fr.atraore.weather_forecast.data.network.WeatherNetworkDataSource
import fr.atraore.weather_forecast.data.network.WeatherNetworkDataSourceImpl
import fr.atraore.weather_forecast.data.provider.LocationProvider
import fr.atraore.weather_forecast.data.provider.LocationProviderImpl
import fr.atraore.weather_forecast.data.repository.ForecastRepository
import fr.atraore.weather_forecast.data.repository.ForecastRepositoryImpl
import fr.atraore.weather_forecast.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class ForecastApplication : Application(), KodeinAware {
  override val kodein = Kodein.lazy {
    import(androidXModule(this@ForecastApplication))

    bind<ForecastDatabase>() with singleton { ForecastDatabase(instance()) }
    bind<CurrentWeatherDao>() with singleton { instance<ForecastDatabase>().currentWeatherDao() }
    bind<WeatherLocationDao>() with singleton { instance<ForecastDatabase>().weatherLocationDao() }
    bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) } //interface
    bind<ApixuWeatherApiService>() with singleton { ApixuWeatherApiService(instance()) }
    bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) } //interface
    bind<LocationProvider>() with singleton { LocationProviderImpl(instance()) }
    bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance()) } //interface
    bind<CurrentWeatherViewModelFactory>() with provider { CurrentWeatherViewModelFactory(instance()) }
  }

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
  }
}
