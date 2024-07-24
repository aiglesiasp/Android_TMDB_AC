package com.aiglepub.architectcoders.data.datasource.remote

import android.location.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

