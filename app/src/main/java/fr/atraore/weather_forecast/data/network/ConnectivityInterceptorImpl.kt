package fr.atraore.weather_forecast.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {

  private val appContext = context.applicationContext

  override fun intercept(chain: Interceptor.Chain): Response {
    if (!isOnline())
      throw IOException("No connectivity")
    return chain.proceed(chain.request())
  }

  private fun isOnline(): Boolean {
    var result = false;
    val connectivitManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      connectivitManager.getNetworkCapabilities(connectivitManager.activeNetwork)?.run {
        if(hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
          result = true;
        }
      }
    } else {
      connectivitManager.activeNetworkInfo?.run {
        if (type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE || type == ConnectivityManager.TYPE_VPN) {
          result = true;
        }
      }
    }
    return result;
  }
}
