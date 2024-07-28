package com.aiglepub.architectcoders.di.repository

import com.aiglepub.architectcoders.data.MoviesRepositoryImpl
import com.aiglepub.architectcoders.data.RegionRepositoryImpl
import com.aiglepub.architectcoders.domain.MoviesRepository
import com.aiglepub.architectcoders.domain.RegionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun bindRegionRepository(impl: RegionRepositoryImpl): RegionRepository
}