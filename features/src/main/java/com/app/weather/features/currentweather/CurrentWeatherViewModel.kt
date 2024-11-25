package com.app.weather.features.currentweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.features.currentweather.data.WeatherData
import com.app.weather.features.currentweather.data.toWeatherData
import com.app.weather.features.currentweather.domain.FetchForecastByCityNameUseCase
import com.app.weather.features.currentweather.domain.FetchForecastByCoordinatesUseCase
import com.app.weather.features.currentweather.domain.GetCityNameUseCase
import com.app.weather.features.currentweather.domain.GetLocationUseCase
import com.app.weather.features.currentweather.presentation.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject





// Navigation Event
sealed class NavigationEvent {
    object NavigateToCityInput : NavigationEvent()
    object None : NavigationEvent()
}

@HiltViewModel
class WeatherViewModel  @Inject constructor(
    private val getCityNameUseCase: GetCityNameUseCase,
    private val getForecastByLocationUseCase: FetchForecastByCoordinatesUseCase,
    private val getForecastByCityNameUseCase: FetchForecastByCityNameUseCase,
    private val getCurrentLocation: GetLocationUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> get() = _weatherState

    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.None)
    val navigationEvent: StateFlow<NavigationEvent> get() = _navigationEvent

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading

            try {
                val cityName = getCityNameUseCase.execute()

                val weatherFlow = if (cityName != null) {
                    getForecastByCityNameUseCase.execute(FetchForecastByCityNameUseCase.Params(cityName))
                } else {
                    val locationData = getCurrentLocation.getLocation()
                    if (locationData == null) {
                        _navigationEvent.value = NavigationEvent.NavigateToCityInput
                        return@launch
                    }
                    getForecastByLocationUseCase.execute(FetchForecastByCoordinatesUseCase.Params(locationData.latitude, locationData.longitude))
                }

                weatherFlow.collect { result ->
                    result.onSuccess { forecastModel ->
                        val weatherData = forecastModel.toWeatherData()
                        _weatherState.value = WeatherState.Success(weatherData)
                    }.onFailure { throwable ->
                        _weatherState.value = WeatherState.Error(throwable.message ?: "Unknown error")
                    }
                }
            } catch (e: Exception) {
                _weatherState.value = WeatherState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = NavigationEvent.None
    }


}

