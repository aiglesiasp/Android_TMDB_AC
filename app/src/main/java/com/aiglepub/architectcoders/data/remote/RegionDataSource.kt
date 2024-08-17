package com.aiglepub.architectcoders.data.remote

import android.location.Location

interface RegionDataSource {
    suspend fun findLastRegion(): String
}
