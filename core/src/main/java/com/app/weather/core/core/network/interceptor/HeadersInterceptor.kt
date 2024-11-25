package com.app.weather.core.core.network.interceptor


import com.app.weather.core.core.network.util.InterceptorUtils.setHeader

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeadersInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        setHeader(
            builder,
            header = "Connection",
            headerValue = "close"
        )
        setHeader(
            builder,
            header = AppHeaders.CONTENT_TYPE,
            headerValue = AppHeaders.APPLICATION_JSON
        )

        setHeader(
            builder,
            header = AppHeaders.ACCEPT,
            headerValue = AppHeaders.APPLICATION_JSON
        )



        return chain.proceed(builder.build())
    }
}