package com.app.weather.features.citynamesearch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.features.citynamesearch.domain.SaveCityNameUseCase
import com.app.weather.features.currentweather.data.toWeatherData
import com.app.weather.features.currentweather.domain.FetchForecastByCityNameUseCase
import com.app.weather.features.currentweather.domain.GetCityNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityNameViewModel @Inject constructor(
    private val fetchForecastByCityNameUseCase: FetchForecastByCityNameUseCase,
    private val saveCityNameUseCase: SaveCityNameUseCase
) : ViewModel() {

    private val _cityNameState = MutableStateFlow<CityNameState>(CityNameState.Idle)
    val cityNameState: StateFlow<CityNameState> get() = _cityNameState

    fun fetchWeatherForCity(cityName: String) {
        viewModelScope.launch {
            _cityNameState.value = CityNameState.Loading

            try {
                val weatherFlow = fetchForecastByCityNameUseCase.execute(
                    FetchForecastByCityNameUseCase.Params(cityName)
                )

                weatherFlow.collect { result ->
                    result.onSuccess { forecastModel ->
                        val weatherData = forecastModel.toWeatherData()
                        _cityNameState.value = CityNameState.Success(weatherData)
                        saveCityName(cityName)
                    }.onFailure { throwable ->
                        _cityNameState.value =
                            CityNameState.Error(throwable.message ?: "Unknown error")
                    }
                }
            } catch (e: Exception) {
                _cityNameState.value = CityNameState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun saveCityName(cityName: String) {
        viewModelScope.launch {
            saveCityNameUseCase.execute(cityName)
        }
    }
}