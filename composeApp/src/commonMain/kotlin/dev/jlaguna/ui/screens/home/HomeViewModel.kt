package dev.jlaguna.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.data.Movie
import dev.jlaguna.data.MoviesRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    fun onUiReady() {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            repository.movies.collect { movies ->
                state = UiState(
                    isLoading = false,
                    movies = movies
                )
            }

            //state = UiState(
            //    isLoading = false,
            //    movies = repository.fetchPopularMovies()
            //)
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}