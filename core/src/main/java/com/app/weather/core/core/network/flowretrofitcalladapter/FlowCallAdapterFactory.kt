package com.app.weather.core.core.network.flowretrofitcalladapter

import com.app.weather.core.core.network.networkresultwrapper.NetworkResult
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class FlowCallAdapterFactory private constructor(private val moshi: Moshi) : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return when (getRawType(returnType)) {
            Flow::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {
                    NetworkResult::class.java -> {
                        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                        FlowCallAdapter<Any>(resultType, moshi)
                    }
                    else -> null
                }
            }
            else -> null
        }
    }

    companion object {
        @JvmStatic
        fun create(moshi: Moshi) = FlowCallAdapterFactory(moshi)
    }
}