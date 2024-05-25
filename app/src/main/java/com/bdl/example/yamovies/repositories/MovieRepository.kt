package com.bdl.example.yamovies.repositories

import MoviesList
import android.util.Log
import com.bdl.example.yamovies.utils.RetrofitInstance
import retrofit2.Response

class MovieRepository {
    suspend fun getPopularMovies(page: Int): Response<MoviesList> {
        var result = RetrofitInstance.movieApiInterface.getPopularMovies(page)
        Log.i("test : ", "my resposne is : ${result}")

        return result

    }

    suspend fun getDetailsById(id: Int, apiKey: String) = RetrofitInstance.movieApiInterface.getDetailsById(id, apiKey)

    suspend fun getTopRatedMovies(page: Int): Response<MoviesList> {
        var result = RetrofitInstance.movieApiInterface.getTopRatedMovies(page = page)
        Log.i("test : ", "my resposne is : ${result}")

        return result
    }

    suspend fun getMovieByGenre(genreID: Int): Response<MoviesList> {
        var result = RetrofitInstance.movieApiInterface.getMovieByGenre(genreID = genreID)
        Log.i("test : ", "my resposne is : ${result}")

        return result
    }
}