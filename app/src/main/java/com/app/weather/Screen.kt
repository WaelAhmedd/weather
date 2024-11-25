package com.app.weather

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object CityNameSearch : Screen("cityNameSearch")
    object WeatherListScreen : Screen("WeatherListScreen")

    fun withArgs(vararg args: String?): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}