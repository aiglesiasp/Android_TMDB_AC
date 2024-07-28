package com.aiglepub.architectcoders.di.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    @Named("movieId")
    fun provideMovieId(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle.get<Int>("movieId") ?: throw IllegalStateException("Missing movieId")
    }
}