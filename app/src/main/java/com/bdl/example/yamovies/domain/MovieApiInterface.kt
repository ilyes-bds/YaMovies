package com.bdl.example.yamovies.domain

import MoviesList
import com.bdl.example.yamovies.models.MovieDetailList
import com.bdl.example.yamovies.utils.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {
    @GET("movie/popular?language=en-US")
    suspend fun getPopularMovies(
        @Query("page")page: Int,
        @Query("api_key")apiKey: String = API_KEY
    ):Response<MoviesList>

    @GET("movie/{id}&language=en-US")
    suspend fun getDetailsById(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieDetailList>

    @GET("movie/top_rated?language=en-US")
    suspend fun getTopRatedMovies(
        @Query("page")page: Int,
        @Query("api_key")apiKey: String = API_KEY
    ):Response<MoviesList>

    @GET("discover/movie?language=en-US")
    suspend fun getMovieByGenre(
        @Query("with_genres")genreID: Int,
        @Query("api_key")apiKey: String = API_KEY
    ):Response<MoviesList>

}