package com.app.weather.features.splash


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.app.weather.core.core.base.SideEffectsKey
import com.app.weather.features.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit
) {
    LaunchedEffect(SideEffectsKey) {
        splashViewModel.effect.onEach { effect ->
            when (effect) {
                is SplashSideEffect.ShowError -> {

                }
                is SplashSideEffect.NavigateToHomeScreen -> {
                    navigateToHomeScreen()
                }
            }
        }.collect()
    }

    LaunchedEffect(Unit) {
        splashViewModel.setEvent(SplashEvent.Start)
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Corn",
                modifier = Modifier.size(300.dp)
            )
        }
    }
}


