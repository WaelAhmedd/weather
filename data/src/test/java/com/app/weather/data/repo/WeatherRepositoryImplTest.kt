package com.app.weather.data.repo

import com.app.weather.data.datasources.WeathersApiService
import com.app.weather.data.models.ForecastModel
import com.app.weather.data.reposimp.WeatherRepositoryImpl
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.IOException

class WeatherRepositoryImplTest {

    @Mock
    private lateinit var apiService: WeathersApiService
    private lateinit var weatherRepo: WeatherRepositoryImpl

    @Before
    fun setup() {

        MockitoAnnotations.openMocks(this)
        weatherRepo = WeatherRepositoryImpl(apiService)
    }

    @Test
    fun `fetchForecastByCoordinates returns successful response`() = runBlockingTest {
        // Mock response
        val mockForecast = ForecastModel(weatherList = emptyList(), cityDtoData = null)
        `when`(apiService.getForecastData(40.7128, -74.0060)).thenReturn((mockForecast))

        // Execute
        val results = weatherRepo.fetchForecastByCoordinates(40.7128, -74.0060).toList()

        // Verify
        assertEquals(Result.success(mockForecast), results.last())
    }

    @Test
    fun `fetchForecastByCityName returns successful response`() = runBlockingTest {
        // Mock response
        val mockForecast = ForecastModel(weatherList = emptyList(), cityDtoData = null)
        `when`(apiService.getForecastDataWithCityName("New York")).thenReturn(mockForecast)

        // Execute
        val results = weatherRepo.fetchForecastByCityName("New York").toList()

        // Verify
        assertEquals(Result.success(mockForecast), results.last())
    }



    @Test
    fun `fetchForecastByCityName handles exception`() = runBlockingTest {
        // Mock exception
        `when`(apiService.getForecastDataWithCityName("New York")).thenThrow(IOException("Network error"))

        // Execute
        val results = weatherRepo.fetchForecastByCityName("New York").toList()

        // Verify
        assert(results.last().isFailure)
        assertEquals("Network error", (results.last().exceptionOrNull() as IOException).message)
    }
}
