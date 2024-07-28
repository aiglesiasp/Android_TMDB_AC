package com.aiglepub.architectcoders.di.remote

import com.aiglepub.architectcoders.data.remote.LocationDataSource
import com.aiglepub.architectcoders.data.remote.MoviesRemoteDataSource
import com.aiglepub.architectcoders.data.remote.RegionDataSource
import com.aiglepub.architectcoders.framework.LocationDataSourceImpl
import com.aiglepub.architectcoders.framework.MoviesRemoteDataSourceImpl
import com.aiglepub.architectcoders.framework.RegionRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    abstract fun bindMoviesRemoteDataSource(impl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource

    @Binds
    abstract fun bindRegionRemoteDataSource(impl: RegionRemoteDataSourceImpl): RegionDataSource

    @Binds
    abstract fun bindLocationDataSource(impl: LocationDataSourceImpl): LocationDataSource


}