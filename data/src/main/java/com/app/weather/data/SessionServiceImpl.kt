package com.app.weather.data

import android.content.Context
import android.content.SharedPreferences
import com.app.weather.data.domain.SessionService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SessionService {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }


    companion object {
        const val SHARED_PREFERENCES_FILE = "app_pref"
        private const val CITY_NAME_KEY = "CITY_NAME_KEY"
    }

    // Save the city name to SharedPreferences
    override fun saveCityName(name: String) {
        sharedPreferences.edit()
            .putString(CITY_NAME_KEY, name)
            .apply() // Use apply for asynchronous saving
    }

    // Retrieve the city name from SharedPreferences
    override fun getCityName(): String? {
        return sharedPreferences.getString(CITY_NAME_KEY, null)
    }
}

