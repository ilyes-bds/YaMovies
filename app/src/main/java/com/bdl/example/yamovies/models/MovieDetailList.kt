package com.bdl.example.yamovies.models

import com.google.gson.annotations.SerializedName

data class MovieDetailList(
    val adult: Boolean = false,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Int = 0,
    val genres: List<Genre> = emptyList(),
    val homepage: String? = null,
    val id: Int = 0,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_title")
    val originalTitle: String = "",
    val overview: String? = null,
    val popularity: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val revenue: Int = 0,
    val runtime: Int? = null,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = emptyList(),
    val status: String = "",
    val tagline: String? = null,
    val title: String = "",
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0
)

data class BelongsToCollection(
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?
)



