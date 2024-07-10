package com.aiglepub.architectcoders.data.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aiglepub.architectcoders.data.Movie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :id")
    suspend fun getMovieById(id: Int): Movie?

    @Query("SELECT COUNT() FROM Movie")
    suspend fun countMovies(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)
}