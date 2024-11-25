package com.app.weather.core.core.network.flowretrofitcalladapter

import com.app.weather.core.core.network.networkresultwrapper.ErrorBody
import com.app.weather.core.core.network.networkresultwrapper.ErrorResponse
import com.app.weather.core.core.network.networkresultwrapper.NetworkResult
import com.app.weather.core.core.network.flowretrofitcalladapter.Constants.MULTIPLE_CHOICES
import com.app.weather.core.core.network.flowretrofitcalladapter.Constants.SUCCESS
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FlowCallAdapter<T>(
    private val responseType: Type,
    private val moshi: Moshi
) : CallAdapter<T, Flow<NetworkResult<T>>> {
    override fun adapt(call: Call<T>): Flow<NetworkResult<T>> {
        return flow {
            emit(
                suspendCancellableCoroutine { continuation ->
                    val clonedCall = call.clone()
                    clonedCall.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            continuation.resumeWithException(t)
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            val code = response.code()
                            val result = if (code in SUCCESS until MULTIPLE_CHOICES) {
                                val body = response.body()
                                NetworkResult.Success(body)
                            } else {
                                val adapter = moshi.adapter(ErrorBody::class.java)
                                NetworkResult.Failure(
                                    ErrorResponse(
                                        response.errorBody()?.string()?.let(adapter::fromJson), code
                                    )
                                )
                            }
                            continuation.resume(result)
                        }
                    })
                    continuation.invokeOnCancellation {
                        clonedCall.cancel()
                    }
                }
            )
        }
    }

    override fun responseType() = responseType
}

object Constants {
    // SERVER ERROR CODES
    const val NOT_FOUND = 404
    const val INTERNAL_SERVER_ERROR = 500
    const val UN_AUTHORIZED = 401
    const val SUCCESS = 200
    const val MULTIPLE_CHOICES = 300
    const val FORBIDDEN = 403
    const val UN_PROCESSABLE_CONTENT = 422
}
