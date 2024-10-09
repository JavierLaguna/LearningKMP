package dev.jlaguna.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.jlaguna.data.MoviesRepository
import dev.jlaguna.data.MoviesService
import dev.jlaguna.data.movies
import dev.jlaguna.ui.screens.detail.DetailScreen
import dev.jlaguna.ui.screens.detail.DetailViewModel
import dev.jlaguna.ui.screens.home.HomeScreen
import dev.jlaguna.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import learningkmp.composeapp.generated.resources.Res
import learningkmp.composeapp.generated.resources.api_key
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val repository = rememberMoviesRepository()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                vm = viewModel {
                    HomeViewModel(repository)
                },
                onMovieClick = { movie ->
                    navController.navigate("detail/${movie.id}")
                }
            )
        }

        composable(
            "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                vm = viewModel{
                    DetailViewModel(movieId, repository)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun rememberMoviesRepository(
    apiKey: String = stringResource(Res.string.api_key)
): MoviesRepository = remember {
    val client = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", apiKey)
                }
            }
    }

    MoviesRepository(MoviesService(client))
}