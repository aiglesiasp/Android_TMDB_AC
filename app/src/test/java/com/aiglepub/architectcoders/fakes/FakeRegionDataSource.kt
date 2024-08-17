package com.aiglepub.architectcoders.fakes

import com.aiglepub.architectcoders.data.remote.RegionDataSource
import com.aiglepub.architectcoders.framework.remote.DEFAULT_REGION

class FakeRegionDataSource: RegionDataSource {

    private var region: String = DEFAULT_REGION
    override suspend fun findLastRegion(): String = region

}