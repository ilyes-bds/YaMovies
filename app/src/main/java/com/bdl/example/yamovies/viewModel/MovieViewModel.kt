package com.bdl.example.yamovies.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bdl.example.yamovies.models.Genre
import com.bdl.example.yamovies.models.Movie
import com.bdl.example.yamovies.models.MovieDetailList
import com.bdl.example.yamovies.paging.PaginationFactory
import com.bdl.example.yamovies.repositories.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    var state by mutableStateOf(MovieListState())
    var id by mutableIntStateOf(0)

    private val pagination = PaginationFactory(
        initialPage = state.page,
        onLoadUpdated = {
            state = state.copy(
                isLoading = it
            )
        },
        onRequest = {nextPage ->
            movieRepository.getPopularMovies(nextPage)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = {items, newPage ->
            state = state.copy(
                movies = state.movies + items.results,
                page = newPage,
                endReached = state.page == 25
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            pagination.loadNextPage()
        }
    }

    fun getDetailsById(genre: Genre) {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)

                val response = movieRepository.getMovieByGenre(genreID = genre.id)

                if (response.isSuccessful) {
                    state = state.copy(
                        movies = response.body()?.results ?: emptyList(),
                        page = 0,
                        title = "${genre.name} Movies",
                        isLoading = false
                    )
                }else{
                    state = state.copy(
                        movies = response.body()?.results ?: emptyList(),
                        page = 0,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)

                val response = movieRepository.getPopularMovies(1)

                state = if (response.isSuccessful) {
                    state.copy(
                        movies = response.body()?.results ?: emptyList(),
                        page = 0,
                        title = "Popular Movies",
                        isLoading = false
                    )
                }else{
                    state.copy(
                        movies = response.body()?.results ?: emptyList(),
                        page = 0,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)

                val response = movieRepository.getTopRatedMovies(1)

                state = if (response.isSuccessful) {
                    state.copy(
                        movies = response.body()?.results ?: emptyList(),
                        page = 0,
                        title = "Top Rated Movies",
                        isLoading = false
                    )
                }else{
                    state.copy(
                        movies = response.body()?.results ?: emptyList(),
                        page = 0,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

}

data class MovieListState(
    val movies: List<Movie> = emptyList(),
    val page: Int = 1,
    val title: String = "Popular Movies",
    val movieDetailsData: MovieDetailList = MovieDetailList(),
    val endReached: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false
)