package com.app.weather.features.weatherlist.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.app.weather.features.weatherlist.DailyWeatherGroup
import com.app.weather.features.weatherlist.HourlyWeather
import com.app.weather.features.weatherlist.WeatherMVIViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherMVIScreen(
    viewModel: WeatherMVIViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather Forecast") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                state.errorMessage != null -> {
                    Text(
                        text = "Error: ${state.errorMessage}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    // List of Unique Dates
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.weatherGroups) { dayGroup ->
                            WeatherRow(dayGroup)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherRow(dailyWeatherGroup: DailyWeatherGroup) {
    val dailyWeather = dailyWeatherGroup.forecasts.first()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberImagePainter(dailyWeather.iconUrl),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = dailyWeatherGroup.date, style = MaterialTheme.typography.labelLarge)
            Text(text = "Temp: ${dailyWeather.temperature}°C")
            Text(text = dailyWeather.condition)
        }
    }
}

