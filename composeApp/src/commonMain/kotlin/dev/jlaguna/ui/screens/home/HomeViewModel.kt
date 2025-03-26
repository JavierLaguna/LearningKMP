package dev.jlaguna.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.data.Movie
import dev.jlaguna.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            repository.movies.collect { movies ->
                _state.value = UiState(
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