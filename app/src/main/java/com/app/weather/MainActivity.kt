package com.app.weather

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            MainNavigation(activity = this, navController = navController)
//
//            if ((uiState.orderDetailsDto == null) or (uiState.orderUIStrategy == null)) {
//                Text(text = "Loading ...", Modifier.statusBarsPadding())
//            } else {
//                OrderScreen(
//                    uiState.orderDetailsDto!!,
//                    uiState.orderUIStrategy!!,
//                    viewModel::toggleStrategy
//                )
//            }
        }
    }
}






