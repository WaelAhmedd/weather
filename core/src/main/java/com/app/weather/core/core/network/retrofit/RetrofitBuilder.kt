package com.app.weather.core.core.network.retrofit

import com.app.weather.core.core.network.base.BaseRetrofit
import com.app.weather.core.core.network.base.RetrofitConfigObject
import com.app.weather.core.core.network.client.HttpClient
import retrofit2.Retrofit

/***
 * used to build retrofit builder
 */
class RetrofitBuilder : BaseRetrofit() {

    fun getRetrofit(retrofitConfigObject: RetrofitConfigObject): Retrofit =
        buildRetrofit(retrofitConfigObject)

    private fun buildRetrofit(retrofitConfigObject: RetrofitConfigObject): Retrofit =
        with(retrofitConfigObject) {
                getRetrofitBuilder(moshi).client(
                    HttpClient.createClient(
                        context,
                        accessTokenInterceptor,
                    )
                ).baseUrl(baseUrl!!).build()

        }
}