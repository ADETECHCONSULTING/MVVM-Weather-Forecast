package fr.atraore.weather_forecast

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//      val w: Window = window
//      w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//      w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//    }
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    bottom_nav.setupWithNavController(navController)

  }

  override fun onSupportNavigateUp(): Boolean {
    return NavigationUI.navigateUp(navController, null)
  }
}
