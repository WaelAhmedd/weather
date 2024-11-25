package com.app.weather.core.core.network.networkresultwrapper

import com.squareup.moshi.Types
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

object WrapperConverter : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val envelopedType = Types.newParameterizedType(BaseResponse::class.java, type)
        val delegate: Converter<ResponseBody, BaseResponse<Any>>? =
            retrofit.nextResponseBodyConverter(this, envelopedType, annotations)

        return Unwrapper(delegate)
    }

    private class Unwrapper<T>(
        private val delegate: Converter<ResponseBody, BaseResponse<T>>?
    ) : Converter<ResponseBody, T> {

        override fun convert(value: ResponseBody): T? {
            return delegate?.convert(value)?.data
        }
    }
}