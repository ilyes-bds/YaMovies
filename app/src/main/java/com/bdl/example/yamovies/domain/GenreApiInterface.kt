package com.bdl.example.yamovies.domain

import com.bdl.example.yamovies.models.GenreList
import com.bdl.example.yamovies.utils.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApiInterface {
    @GET("genre/movie/list?language=en")
    suspend fun getGenres(
        @Query("api_key")apiKey: String = API_KEY
    ):Response<GenreList>
}