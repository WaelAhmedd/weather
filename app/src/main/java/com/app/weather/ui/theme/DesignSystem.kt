package com.app.weather.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Title(text: String, modifier: Modifier = Modifier) = Text(
    text = text,
    modifier = modifier,
    style = MaterialTheme.typography.titleMedium
)

@Composable
fun SubTitle(text: String) =
    Text(text = text, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)


