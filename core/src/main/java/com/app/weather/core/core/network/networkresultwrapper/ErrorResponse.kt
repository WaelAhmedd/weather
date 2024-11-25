package com.app.weather.core.core.network.networkresultwrapper

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

data class ErrorResponse(val body: ErrorBody?, val code: Int?)

data class ErrorBody(
    val message: String?,
    val errors: Map<String, List<String>>?
)

data class Error(val message: String, val errors: Map<String, String>)


fun mapErrorResponse(response: String): ErrorBody? {
    try {
        val json = JSONObject(response)
        Log.d("TAG", "mapErrorResponse: $json")
        val message = json.getString("message")
        val errors = mutableMapOf<String, List<String>>()
         try {
            val errorsJson = json.getJSONObject("errors")
            val keys = errorsJson.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val value = errorsJson.getString(key)
                errors[key] = listOf(value)
            }
        } catch (e: Exception) {
             Log.e("TAG", "mapErrorResponse: ", e)
        }




        return ErrorBody(message, errors)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return null
}
