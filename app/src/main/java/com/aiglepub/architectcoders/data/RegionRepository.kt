package com.aiglepub.architectcoders.data

import com.aiglepub.architectcoders.data.datasource.local.RegionDataSource

class RegionRepository(private val regionDataSource: RegionDataSource) {
    suspend fun findLastRegion(): String = regionDataSource.findLastRegion()
}