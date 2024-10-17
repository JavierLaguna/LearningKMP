package dev.jlaguna.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.data.Movie
import dev.jlaguna.data.MoviesRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val repository: MoviesRepository
): ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            repository.fetchMovieById(movieId).collect { movie ->
                state = UiState(
                    isLoading = false,
                    movie = movie
                )
            }

            //state = UiState(
            //    isLoading = false,
            //    movie = repository.fetchMovieById(movieId)
            //)
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val movie: Movie? = null
    )

    fun onFavoriteClick() {
        state.movie?.let {
            viewModelScope.launch {
                repository.toggleFavorite(it)
            }
        }
    }
}