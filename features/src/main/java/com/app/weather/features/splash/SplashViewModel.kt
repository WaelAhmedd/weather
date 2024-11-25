/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.weather.features.splash

import android.os.Handler
import android.os.Looper
import com.app.weather.core.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(


    ) : BaseViewModel<SplashState, SplashEvent, SplashSideEffect>() {

    private fun startSplash() {

        Handler(Looper.getMainLooper()).postDelayed({
            setEffect { SplashSideEffect.NavigateToHomeScreen }
        }, 3000)
    }



    override fun setInitialState(): SplashState = SplashState.Init

    override fun handleEvents(event: SplashEvent) {
        when (event) {
            is SplashEvent.Start -> {
                startSplash()
            }
        }
    }

}


