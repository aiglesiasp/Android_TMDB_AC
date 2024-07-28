package com.aiglepub.architectcoders.framework.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDb(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val poster: String,
    val backdrop: String,
    val originalTitle: String,
    val originalLanguage: String,
    val popularity: Double,
    val voteAverage: Double,
    val favorite: Boolean
)