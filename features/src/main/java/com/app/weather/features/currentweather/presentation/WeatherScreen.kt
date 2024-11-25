package com.app.weather.features.currentweather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.weather.features.R
import com.app.weather.features.currentweather.NavigationEvent
import com.app.weather.features.currentweather.WeatherViewModel
import com.app.weather.features.currentweather.data.WeatherData

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    onNavigateToCityInput: () -> Unit,
    onNavigateToPreviousWeather: () -> Unit
) {
    val weatherState by viewModel.weatherState.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToCityInput -> {
                onNavigateToCityInput()
                viewModel.clearNavigationEvent()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (weatherState) {
            is WeatherState.Loading -> {
                LoadingContent()
            }
            is WeatherState.Success -> {
                val weatherData = (weatherState as WeatherState.Success).weatherData
                WeatherContent(
                    weatherData = weatherData,
                    onNavigateToCityInput = onNavigateToCityInput,
                    onNavigateToPreviousWeather = onNavigateToPreviousWeather
                )
            }
            is WeatherState.Error -> {
                val errorMessage = (weatherState as WeatherState.Error).message
                ErrorContent(errorMessage)
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun WeatherContent(
    weatherData: WeatherData,
    onNavigateToCityInput: () -> Unit,
    onNavigateToPreviousWeather: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            // City Name
            Text(
                text = weatherData.cityName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Weather Icon
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with appropriate icon
                contentDescription = "Weather Icon",
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Temperature and Condition
            Text(
                text = "${weatherData.temperature}°C",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = weatherData.condition,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weather Overview Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Weather Overview",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Feels Like: ${weatherData.feelsLike}°C", style = MaterialTheme.typography.bodyLarge)
            Text("Pressure: ${weatherData.pressure} hPa", style = MaterialTheme.typography.bodyLarge)
            Text("Humidity: ${weatherData.humidity}%", style = MaterialTheme.typography.bodyLarge)
            Text("Main: ${weatherData.mainDescription}", style = MaterialTheme.typography.bodyLarge)
            Text("Details: ${weatherData.description}", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.weight(1f)) // Push buttons closer to the center

        // Buttons Section
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNavigateToCityInput,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Enter City Name",
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                )
            }
            Button(
                onClick = onNavigateToPreviousWeather,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = "See Previous Weather Data",
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSecondary)
                )
            }
        }
    }
}


@Composable
fun ErrorContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
    }
}
