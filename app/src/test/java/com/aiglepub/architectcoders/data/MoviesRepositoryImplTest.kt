package com.aiglepub.architectcoders.data

import com.aiglepub.architectcoders.data.local.MoviesLocalDataSource
import com.aiglepub.architectcoders.data.remote.MoviesRemoteDataSource
import com.aiglepub.architectcoders.domain.MoviesRepository
import com.aiglepub.architectcoders.domain.RegionRepository
import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.helpers.generateListMovies
import com.aiglepub.architectcoders.helpers.generateSampleMovie
import com.aiglepub.architectcoders.framework.remote.DEFAULT_REGION
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryImplTest {

    @Mock
    lateinit var regionRepository: RegionRepository

    @Mock
    lateinit var remoteDataSource: MoviesRemoteDataSource

    @Mock
    lateinit var  localDataSource: MoviesLocalDataSource

    private lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        repository = MoviesRepositoryImpl(regionRepository, remoteDataSource, localDataSource)
    }

    @Test
    fun `Movies are taken from local data source if available`(): Unit = runBlocking {
        //GIVEN
        val localMovies = generateListMovies(1,2,3)
        whenever(localDataSource.movies).thenReturn(flowOf(localMovies))
        //WHEN
        val result = repository.movies
        //THEN
        assertEquals(localMovies, result.first())
    }

    @Test
    fun `Movies are saved to local data source when it's empty`(): Unit = runBlocking {
        //GIVEN
        val localMovies = emptyList<Movie>()
        val remoteMovies = generateListMovies(1,2,3)
        whenever(localDataSource.movies).thenReturn(flowOf(localMovies))
        whenever(regionRepository.findLastRegion()).thenReturn(DEFAULT_REGION)
        whenever(remoteDataSource.fetchPopularMovies(DEFAULT_REGION)).thenReturn(remoteMovies)
        //WHEN
        repository.movies.first()
        //THEN
        verify(localDataSource).insertMovies(remoteMovies)
    }

    /*
    @Test
    fun `Movie are take from local data source if available`(): Unit = runBlocking {
        //GIVEN
        val localMovie = generateSampleMovie(1)
        whenever(localDataSource.getMovieById(1)).thenReturn(flowOf(localMovie))
        //WHEN
        val result = repository.findMovieById(1)
        //THEN
        assertEquals(localMovie, result)
    }
    */

    @Test
    fun `Toggling favorite updates local data source`(): Unit = runBlocking {
        //GIVEN
        val movie = generateSampleMovie(3)
        //WHEN
        repository.toggleFavorite(movie)
        //THEN
        verify(localDataSource).insertMovies(argThat { get(0).id == 3 })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite movie`(): Unit = runBlocking {
        //GIVEN
        val movie = generateSampleMovie(3).copy(favorite = false)
        //WHEN
        repository.toggleFavorite(movie)
        //THEN
        verify(localDataSource).insertMovies(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite movie`(): Unit = runBlocking {
        //GIVEN
        val movie = generateSampleMovie(3).copy(favorite = true)
        //WHEN
        repository.toggleFavorite(movie)
        //THEN
        verify(localDataSource).insertMovies(argThat { !get(0).favorite })
    }
}