package com.app.weather.core.core.network.retrofit

import android.content.Context
import com.app.weather.core.BuildConfig

import com.app.weather.core.core.network.interceptor.AccessTokenInterceptor
import com.app.weather.core.core.network.base.BaseServices
import com.app.weather.core.core.network.base.RetrofitConfigObject
import com.squareup.moshi.Moshi

/***
 * this is singleton for the  services which used the default configuration
 * and in its constructor we call createRetrofit which decide which retrofit object must be used based on the params
 */
class Services(
    context: Context,
    accessTokenInterceptor: AccessTokenInterceptor,
    moshi: Moshi,
) : BaseServices() {

    init {
        createRetrofit(
            RetrofitConfigObject(
                BuildConfig.BASE_URL,
                context,
                accessTokenInterceptor,
                moshi,

                )
        )
    }

    companion object {
        private var mInstance: Services? = null

        @Synchronized
        fun getInstance(
            context: Context,
            accessTokenInterceptor: AccessTokenInterceptor,
            moshi: Moshi,
        ): Services {
            if (mInstance == null) {
                mInstance = Services(
                    context,
                    accessTokenInterceptor,
                    moshi,
                )
            }
            return mInstance!!
        }
    }
}