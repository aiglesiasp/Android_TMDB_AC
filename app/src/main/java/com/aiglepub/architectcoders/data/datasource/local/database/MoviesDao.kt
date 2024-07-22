package com.aiglepub.architectcoders.data.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aiglepub.architectcoders.data.datasource.local.MovieDb
import com.aiglepub.architectcoders.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM MovieDb")
    fun getAllMovies(): Flow<List<MovieDb>>

    @Query("SELECT * FROM MovieDb WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieDb?>

    @Query("SELECT COUNT() FROM MovieDb")
    suspend fun countMovies(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDb>)
}