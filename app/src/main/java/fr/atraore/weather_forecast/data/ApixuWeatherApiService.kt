package fr.atraore.weather_forecast.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import fr.atraore.weather_forecast.data.network.ConnectivityInterceptor
import fr.atraore.weather_forecast.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "c539670dc9374859db958098c6a30c2f"
//http://api.weatherstack.com/current?access_key=c539670dc9374859db958098c6a30c2f&query=Paris

interface ApixuWeatherApiService {

  @GET("current")
  fun getCurrentWeather(
    @Query(value = "query") location: String
  ) : Deferred<CurrentWeatherResponse>

  companion object {
    operator fun invoke(
      connectivityInterceptor: ConnectivityInterceptor
    ): ApixuWeatherApiService {
      val requestInterceptor = Interceptor {chain ->
        val url = chain.request()
          .url()
          .newBuilder()
          .addQueryParameter("access_key", API_KEY)
          .build()
        val request = chain.request()
          .newBuilder()
          .url(url)
          .build()
        return@Interceptor chain.proceed(request)
      }

      val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(connectivityInterceptor)
        .build()

      return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://api.weatherstack.com/")
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApixuWeatherApiService::class.java)
    }
  }
}
