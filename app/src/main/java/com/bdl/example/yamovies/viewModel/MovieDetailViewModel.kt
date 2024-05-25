package com.bdl.example.yamovies.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bdl.example.yamovies.models.MovieDetailList
import com.bdl.example.yamovies.repositories.MovieRepository
import com.bdl.example.yamovies.utils.Util.API_KEY
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieId: Int) : ViewModel() {
    private val movieRepository = MovieRepository()
    private val apiKey = API_KEY

    var state = mutableStateOf(DetailsState())
        private set

    init {
        fetchMovieDetails(movieId)
    }

    private fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            try {
                val response = movieRepository.getDetailsById(id = movieId, apiKey = apiKey)

                if (response.isSuccessful) {
                    val details = response.body()
                    if (details != null) {
                        Log.i("test", "my data is : ${details.title}")
                    }
                    if (details != null) {
                        state.value = state.value.copy(detailsMovie = details, isLoading = false)
                    } else {
                        state.value = state.value.copy(error = "No data available", isLoading = false)
                    }
                } else {
                    state.value = state.value.copy(error = "Error: ${response.message()}", isLoading = false)
                }
            } catch (e: Exception) {
                Log.i("test", "my error is : ${e.message}")
                state.value = state.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}

data class DetailsState(
    val detailsMovie: MovieDetailList = MovieDetailList(),
    val error: String? = null,
    val isLoading: Boolean = false
)




class MovieDetailViewModelFactory(private val movieId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
