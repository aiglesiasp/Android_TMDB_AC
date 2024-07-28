package com.aiglepub.architectcoders.data

import com.aiglepub.architectcoders.data.remote.RegionDataSource
import com.aiglepub.architectcoders.domain.RegionRepository
import javax.inject.Inject


class RegionRepositoryImpl @Inject constructor(
    private val regionDataSource: RegionDataSource
) : RegionRepository {
    override suspend fun findLastRegion(): String = regionDataSource.findLastRegion()
}