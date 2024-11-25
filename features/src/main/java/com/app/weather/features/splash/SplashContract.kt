package com.app.weather.features.splash

import com.app.weather.core.core.base.ViewEvent
import com.app.weather.core.core.base.ViewSideEffect
import com.app.weather.core.core.base.ViewState


sealed class SplashEvent : ViewEvent {
    object Start : SplashEvent()

}

sealed class SplashState : ViewState {
    object Init : SplashState()

}

sealed class SplashSideEffect : ViewSideEffect {
    data class ShowError(val message: String) : SplashSideEffect()
    object NavigateToHomeScreen : SplashSideEffect()
}