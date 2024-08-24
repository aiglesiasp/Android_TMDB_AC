package com.aiglepub.architectcoders

import androidx.test.rule.GrantPermissionRule
import com.aiglepub.architectcoders.domain.MoviesRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ExampleInstrumentedTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    @Inject
    lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
       hiltRule.inject()
    }

    @Test
    fun test_it_works() {
        runBlocking {
            val movies = repository.movies.first()
            assertTrue(movies.isEmpty())
        }
    }
}