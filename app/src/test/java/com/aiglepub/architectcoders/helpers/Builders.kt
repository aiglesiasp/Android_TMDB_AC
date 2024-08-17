package com.aiglepub.architectcoders.helpers

import com.aiglepub.architectcoders.data.MoviesRepositoryImpl
import com.aiglepub.architectcoders.data.RegionRepositoryImpl
import com.aiglepub.architectcoders.domain.MoviesRepository
import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.domain.usecases.FetchMoviesUseCase
import com.aiglepub.architectcoders.fakes.FakeLocalDataSource
import com.aiglepub.architectcoders.fakes.FakeRegionDataSource
import com.aiglepub.architectcoders.fakes.FakeRemoteDataSource
import com.aiglepub.architectcoders.ui.screens.home.HomeViewModel

fun buildMoviesRepositoryWith(
    localData: List<Movie> = emptyList(),
    remoteData: List<Movie> = emptyList(),
): MoviesRepository {
    val regionRepository = RegionRepositoryImpl(FakeRegionDataSource())
    val localDataSource = FakeLocalDataSource().apply { inMemoryMovies.value = localData }
    val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteData }
    val moviesRepository = MoviesRepositoryImpl(regionRepository, remoteDataSource, localDataSource)
    return moviesRepository
}

fun buildMoviesViewModelWith(
    localData: List<Movie> = emptyList(),
    remoteData: List<Movie> = emptyList()
): HomeViewModel {
    val repository = buildMoviesRepositoryWith(localData = localData, remoteData = remoteData)
    val useCase = FetchMoviesUseCase(repository)
    return HomeViewModel(useCase)
}