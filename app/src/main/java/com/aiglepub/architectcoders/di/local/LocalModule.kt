package com.aiglepub.architectcoders.di.local

import com.aiglepub.architectcoders.data.local.MoviesLocalDataSource
import com.aiglepub.architectcoders.framework.MoviesLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Binds
    abstract fun bindMoviesLocalDataSource(impl: MoviesLocalDataSourceImpl): MoviesLocalDataSource

}