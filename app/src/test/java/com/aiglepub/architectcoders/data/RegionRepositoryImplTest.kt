package com.aiglepub.architectcoders.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RegionRepositoryImplTest{

    @Test
    fun `findLastRegion calls RegionDataSource`(): Unit = runBlocking {
        //GIVEN
        val repository = RegionRepositoryImpl(mock {
            onBlocking { findLastRegion() } doReturn "ES"
        })
        //WHEN
        val result = repository.findLastRegion()
        //THEN
        assertEquals("ES", result)
    }
}