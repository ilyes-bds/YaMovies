package com.bdl.example.yamovies.models

import com.google.gson.annotations.SerializedName

data class GenreList (

    @SerializedName("genres" ) var genres : ArrayList<Genre> = arrayListOf()

)