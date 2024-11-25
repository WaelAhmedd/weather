package com.app.weather.data.datasource


import com.app.weather.core.MockResponses
import com.app.weather.data.datasources.WeathersApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeathersApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: WeathersApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeathersApiService::class.java)
    }



    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
    @Test
    fun getForecastData_sendsCorrectParameters() = runBlocking {
        // Mock Response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(MockResponses.forecastResponse) // JSON response string
        mockWebServer.enqueue(mockResponse)

        // Perform API call
        val response = apiService.getForecastData(
            latitude = 40.7128,
            longitude = -74.0060
        )

        // Assertions
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method) // Correct HTTP method
        assertNotNull(response) // Response should not be null
    }

    @Test
    fun getForecastDataWithCityName_sendsCorrectParameters() = runBlocking {
        // Mock Response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(MockResponses.forecastResponse) // JSON response string
        mockWebServer.enqueue(mockResponse)

        // Perform API call
        val response = apiService.getForecastDataWithCityName(cityName = "New York")

        // Assertions
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method) // Correct HTTP method
        assertNotNull(response) // Response should not be null
    }

}
