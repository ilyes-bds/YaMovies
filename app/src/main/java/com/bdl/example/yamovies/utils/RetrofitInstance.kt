package com.bdl.example.yamovies.utils

import com.bdl.example.yamovies.domain.GenreApiInterface
import com.bdl.example.yamovies.domain.MovieApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val movieApiInterface: MovieApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiInterface::class.java)
    }
    val genreApiInterface: GenreApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GenreApiInterface::class.java)
    }
}