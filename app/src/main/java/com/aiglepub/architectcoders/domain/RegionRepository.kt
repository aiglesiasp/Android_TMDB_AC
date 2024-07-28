package com.aiglepub.architectcoders.domain

interface RegionRepository {
    suspend fun findLastRegion(): String
}