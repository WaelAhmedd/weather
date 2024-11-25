package com.app.weather.core.core.network.base

import android.content.Context
import com.app.weather.core.core.network.interceptor.AccessTokenInterceptor
import com.app.weather.core.core.network.retrofit.RetrofitBuilder
import com.squareup.moshi.Moshi
import retrofit2.Retrofit

/***
 * BaseServices this class act with the factory design pattern
 * it take a parameters and depend on them it decide to create retrofit using  httpRetrofit or HttpsRetrofit
 */
@Suppress("UnnecessaryAbstractClass")
abstract class BaseServices {
    lateinit var retrofit: Retrofit

    fun createRetrofit(retrofitConfigObject: RetrofitConfigObject) {
        retrofit = RetrofitBuilder().getRetrofit(retrofitConfigObject)
    }
}

data class RetrofitConfigObject(
    val baseUrl: String?,
    val context: Context?,
    val accessTokenInterceptor: AccessTokenInterceptor, val moshi: Moshi
)