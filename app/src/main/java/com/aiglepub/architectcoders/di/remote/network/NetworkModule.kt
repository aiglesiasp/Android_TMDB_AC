package com.aiglepub.architectcoders.di.remote.network

import android.content.Context
import android.location.Geocoder
import com.aiglepub.architectcoders.framework.api.MovieService
import com.aiglepub.architectcoders.framework.api.MoviesClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMoviesClient(@Named("apiKey") apiKey: String) = MoviesClient(apiKey).instance

    @Provides
    fun provideFusedLocationproviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context)
    }
}