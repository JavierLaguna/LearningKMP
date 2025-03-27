package dev.jlaguna.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.data.Movie
import dev.jlaguna.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailViewModel(
    private val movieId: Int
): ViewModel(), KoinComponent {

    private val repository: MoviesRepository by inject()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            repository.fetchMovieById(movieId).collect { movie ->
                _state.value = UiState(
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
        _state.value.movie?.let {
            viewModelScope.launch {
                repository.toggleFavorite(it)
            }
        }
    }
}