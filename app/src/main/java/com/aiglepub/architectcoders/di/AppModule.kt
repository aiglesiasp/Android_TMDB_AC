package com.aiglepub.architectcoders.di

import com.aiglepub.architectcoders.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(): String = BuildConfig.TMDB_API_KEY
}