package dev.jlaguna.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jlaguna.data.Movie
import dev.jlaguna.data.MoviesService
import dev.jlaguna.data.RemoteMovie
import kotlinx.coroutines.launch

class HomeViewModel(
    private val moviesService: MoviesService
): ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            state = UiState(
                isLoading = false,
                movies = moviesService.fetchPopularMovies().results.map { it.toDomainMovie() }
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}

private fun RemoteMovie.toDomainMovie() = Movie(
    id = id,
    title = title,
    poster = "https://image.tmdb.org/t/p/w500/${posterPath}"
)