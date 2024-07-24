package com.aiglepub.architectcoders.data.remote

import android.location.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

