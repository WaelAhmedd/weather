package com.app.weather.core.core.utils

object ImageUtils {

    fun constructPosterUrl(posterPath: String?): String {
        return if (!posterPath.isNullOrEmpty()) {
            "https://image.tmdb.org/t/p/w500$posterPath"
        } else {
            ""
        }
    }
}
