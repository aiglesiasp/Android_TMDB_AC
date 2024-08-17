package com.aiglepub.architectcoders.helpers

import com.aiglepub.architectcoders.domain.entities.Movie

fun generateSampleMovie(id: Int) = Movie(
    id = id,
    title = "Title $id",
    overview = "Overview $id",
    releaseDate = "Release Date $id",
    poster = "Poster $id",
    backdrop = "Backdrop $id",
    originalTitle = "Original Title $id",
    originalLanguage = "Original Language $id",
    popularity = id.toDouble(),
    voteAverage = id.toDouble(),
    favorite = id % 2 == 0

)

fun generateListMovies(vararg ids: Int) = ids.map { generateSampleMovie(it) }