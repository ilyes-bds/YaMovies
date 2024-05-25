package com.bdl.example.yamovies.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bdl.example.yamovies.models.Genre
import com.bdl.example.yamovies.repositories.GenreRepository
import kotlinx.coroutines.launch

class GenreViewModel : ViewModel() {

    private val genreRepository = GenreRepository()
    var state by mutableStateOf(GenreState())
    var id by mutableIntStateOf(0)

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            val response = genreRepository.getGenres()
            print("my genres are $response")
            if(response.body()?.genres  != null){
                state = state.copy(
                    genres = response.body()!!.genres
                )

            }

        }


    }


}

data class GenreState(
    val genres: List<Genre> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)