package com.bdl.example.yamovies.utils

object Util {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "c9856d0cb57c3f14bf75bdc6c063b8f3"

    fun formatRuntime(runtime: Int?): String {
        if (runtime == null) return "Unknown"
        val hours = runtime / 60
        val minutes = runtime % 60
        return "${hours}h ${minutes}min"
    }

}