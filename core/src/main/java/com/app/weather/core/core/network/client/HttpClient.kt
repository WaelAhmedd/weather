package com.app.weather.core.core.network.client

import android.content.Context
import com.app.weather.core.BuildConfig
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager

import com.app.weather.core.core.network.interceptor.AccessTokenInterceptor
import com.app.weather.core.core.network.interceptor.HeadersInterceptor
import com.app.weather.core.core.network.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object HttpClient {

    internal fun createClient(
        context: Context?,
        accessTokenInterceptor: AccessTokenInterceptor,
    ): OkHttpClient {
        OkHttpClient()
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        context?.let {
            builder.addInterceptor(HeadersInterceptor())

        }
        builder.addInterceptor(accessTokenInterceptor)
        builder.retryOnConnectionFailure(true)
        builder.hostnameVerifier { _, _ -> true }
        builder.sslSocketFactory(createInsecureSslSocketFactory(), createInsecureTrustManager())

//         Network Logging
        if (BuildConfig.enableNetworkLogging) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            builder.addInterceptor(httpLoggingInterceptor)
            context?.let { builder.addInterceptor(initChuckerInterceptor(it)) }
        }

//        builder.hostnameVerifier { s, _ ->
//            BuildConfig.BASE_URL.contains(s)
//        }

        builder.connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
        builder.callTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)

        //Todo cache related logic to be discussed
//        builder.cache(null) // Disables cache on global level

        return builder.build()
    }

    private fun initChuckerInterceptor(context: Context) = ChuckerInterceptor.Builder(context)
        .collector(initChuckerCollector(context))
        .maxContentLength(length = 250_000L)
        // TODO to be removed if needed
        .redactHeaders("Auth-Token", "Bearer")
        .alwaysReadResponseBody(true)
        .build()

    private fun initChuckerCollector(context: Context) = ChuckerCollector(
        context = context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    private fun createInsecureSslSocketFactory(): SSLSocketFactory {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        return sslContext.socketFactory
    }

    private fun createInsecureTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    }
}