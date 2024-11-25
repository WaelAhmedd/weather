package com.app.weather.features.weatherlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.data.models.ForecastModel
import com.app.weather.features.currentweather.domain.FetchForecastByCityNameUseCase
import com.app.weather.features.currentweather.domain.GetCityNameUseCase
import com.app.weather.features.weatherlist.presentation.WeatherIntent
import com.app.weather.features.weatherlist.presentation.WeatherViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherMVIViewModel @Inject constructor(
    private val fetchForecastByCityNameUseCase: FetchForecastByCityNameUseCase,
    private val getCityNameUseCase: GetCityNameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherViewState())
    val state: StateFlow<WeatherViewState> get() = _state

    init {
        processIntent(WeatherIntent.LoadWeather)
    }

    fun processIntent(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.LoadWeather -> fetchWeatherForCity()
            is WeatherIntent.SelectDay -> filterWeatherByDay(intent.date)
        }
    }

    private fun fetchWeatherForCity() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val cityName = getCityNameUseCase.execute() ?: run {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "City name not found."
                )
                return@launch
            }

            val weatherFlow = fetchForecastByCityNameUseCase.execute(
                FetchForecastByCityNameUseCase.Params(cityName)
            )

            weatherFlow.collect { result ->
                result.onSuccess { forecastModel ->
                    val groupedWeather = groupWeatherByDays(forecastModel)
                    _state.value = _state.value.copy(
                        isLoading = false,
                        weatherGroups = groupedWeather,
                        filteredWeather = groupedWeather.map { it.forecasts.first() }
                    )
                }.onFailure { throwable ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    private fun filterWeatherByDay(date: String) {
        val selectedGroup = _state.value.weatherGroups.find { it.date == date }
        _state.value = _state.value.copy(
            filteredWeather = selectedGroup?.forecasts ?: emptyList()
        )
    }

    private fun groupWeatherByDays(forecastModel: ForecastModel): List<DailyWeatherGroup> {
        return forecastModel.weatherList
            .groupBy { it.date.split(" ")[0] }
            .map { (date, hourlyForecasts) ->

                val representativeWeather = hourlyForecasts.first()
                DailyWeatherGroup(
                    date = date,
                    forecasts = listOf(
                        HourlyWeather(
                            time = "All Day",
                            temperature = representativeWeather.weatherData.temp,
                            condition = representativeWeather.weatherStatus[0].description,
                            iconUrl = ""
                        )
                    )
                )
            }
    }

}
