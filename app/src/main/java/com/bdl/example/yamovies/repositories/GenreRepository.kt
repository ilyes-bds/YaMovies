package com.bdl.example.yamovies.repositories

import android.util.Log
import com.bdl.example.yamovies.models.GenreList
import com.bdl.example.yamovies.utils.RetrofitInstance
import retrofit2.Response

class GenreRepository {
    suspend fun getGenres(): Response<GenreList>{
        var result = RetrofitInstance.genreApiInterface.getGenres()
        Log.i("test : ","my genre response is : ${result}")
        return result

    }

}