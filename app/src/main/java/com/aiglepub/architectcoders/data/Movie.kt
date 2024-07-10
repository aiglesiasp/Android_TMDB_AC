package com.aiglepub.architectcoders.data

import androidx.room.Entity

@Entity
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val poster: String,
    val backdrop: String,
    val originalTitle: String,
    val originalLanguage: String,
    val popularity: Double,
    val voteAverage: Double
)

/*
val movies = (1..100).map {
    Movie(
        id = it,
        title = "Movie $it",
        poster = "https://picsum.photos/200/300?id=$it"
    )
}

 */