package com.app.weather

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.weather.features.citynamesearch.presentation.EnterCityNameScreen
import com.app.weather.features.currentweather.presentation.WeatherScreen

import com.app.weather.features.splash.SplashScreen
import com.app.weather.features.weatherlist.presentation.WeatherMVIScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigation(
    activity: Activity,
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            WeatherScreen(
                onNavigateToPreviousWeather = {navController.navigate(Screen.WeatherListScreen.route)},
                onNavigateToCityInput = { navController.navigate(Screen.CityNameSearch.route) })
        }

        composable(Screen.CityNameSearch.route) {
            EnterCityNameScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.WeatherListScreen.route) {
            WeatherMVIScreen(onBack = { navController.popBackStack() })
        }

    }
}