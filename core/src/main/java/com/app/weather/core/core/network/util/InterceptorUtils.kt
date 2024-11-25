package com.app.weather.core.core.network.util

import okhttp3.Interceptor
import okhttp3.Request

object InterceptorUtils {

    fun hasHeader(chain: Interceptor.Chain, header: String): Boolean =
        chain.request().headers.names().contains(getHeaderFormattedNoColon(header))

    fun setHeader(builder: Request.Builder, header: String, headerValue: String) {
        builder.removeHeader(getHeaderFormattedNoColon(header))
        builder.header(getHeaderFormattedNoColon(header), headerValue)
    }

    private fun getHeaderFormattedNoColon(header: String) =
        header.replace(": placeholder", "")
}