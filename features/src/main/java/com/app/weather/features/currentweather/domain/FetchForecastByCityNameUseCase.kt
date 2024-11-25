package com.app.weather.features.currentweather.domain



import com.app.weather.data.models.ForecastModel
import com.app.weather.data.domain.UseCase
import com.app.weather.data.domain.repos.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchForecastByCityNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) : UseCase<FetchForecastByCityNameUseCase.Params, Result<ForecastModel>> {

    data class Params(val cityName: String)

    override fun execute(params: Params): Flow<Result<ForecastModel>> {
        return weatherRepository.fetchForecastByCityName(cityName = params.cityName)
    }
}
