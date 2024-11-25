package com.app.weather.features.citynamesearch.presentation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.app.weather.features.R
import com.app.weather.features.currentweather.NavigationEvent
import com.app.weather.features.currentweather.WeatherViewModel
import com.app.weather.features.currentweather.data.WeatherData

@Composable
fun EnterCityNameScreen(
    viewModel: CityNameViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val cityNameState by viewModel.cityNameState.collectAsState()
    var cityName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter City Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { viewModel.fetchWeatherForCity(cityName) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))


        when (cityNameState) {
            is CityNameState.Idle -> {
                Text("Enter a city name to fetch weather data.")
            }
            is CityNameState.Loading -> {
                CircularProgressIndicator()
            }
            is CityNameState.Success -> {
                val weatherData = (cityNameState as CityNameState.Success).weatherData
                WeatherContent(weatherData)
            }
            is CityNameState.Error -> {
                val errorMessage = (cityNameState as CityNameState.Error).message
                ErrorContent(
                    message = errorMessage,
                    onRetry = { viewModel.fetchWeatherForCity(cityName) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Back")
        }
    }
}

@Composable
fun WeatherContent(
    weatherData: WeatherData,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {

            Text(
                text = weatherData.cityName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            Spacer(modifier = Modifier.height(8.dp))


            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


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

        Spacer(modifier = Modifier.height(8.dp))


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


    }
}

@Composable
fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
