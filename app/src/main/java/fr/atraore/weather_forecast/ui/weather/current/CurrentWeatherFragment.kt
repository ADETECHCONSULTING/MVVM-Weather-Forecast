package fr.atraore.weather_forecast.ui.weather.current

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import fr.atraore.weather_forecast.R
import fr.atraore.weather_forecast.data.ApixuWeatherApiService
import fr.atraore.weather_forecast.data.network.ConnectivityInterceptorImpl
import fr.atraore.weather_forecast.data.network.WeatherNetworkDataSource
import fr.atraore.weather_forecast.data.network.WeatherNetworkDataSourceImpl
import fr.atraore.weather_forecast.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
  override val kodein by closestKodein()
  private val viewModelFactory by instance<CurrentWeatherViewModelFactory>()

  companion object {
    fun newInstance() =
      CurrentWeatherFragment()
  }

  private lateinit var viewModel: CurrentWeatherViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.current_weather_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
    bindUI()
  }

  private fun bindUI() = launch {
    val currentWeather = viewModel.weather.await()
    currentWeather.observe(viewLifecycleOwner, Observer {
      if (it == null) return@Observer
      textViewCity.text = it.toString()
    })
  }

}
