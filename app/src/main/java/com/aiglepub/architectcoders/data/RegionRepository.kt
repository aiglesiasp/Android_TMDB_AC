package com.aiglepub.architectcoders.data

import com.aiglepub.architectcoders.data.datasource.remote.RegionRemoteDataSource

class RegionRepository(private val regionDataSource: RegionRemoteDataSource) {
    suspend fun findLastRegion(): String = regionDataSource.findLastRegion()
}