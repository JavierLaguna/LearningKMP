package dev.jlaguna.data

data class Movie(
    val id: Int,
    val title: String,
    val poster: String
)

val movies = (1..100).map {
    Movie(
        it,
        "Movie $it",
        poster = "https://picsum.photos/200/300?random=$it"
    )
}