package com.aiglepub.architectcoders.framework.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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