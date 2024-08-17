package com.aiglepub.architectcoders.ui.screens.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.helpers.generateListMovies
import com.aiglepub.architectcoders.ui.common.LOADING_INDICATOR_TAG
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadinState_showProgress(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                state = Result.Loading,
                onClick = {}
            )
        }

        onNodeWithTag(LOADING_INDICATOR_TAG).assertIsDisplayed()
    }

    @Test
    fun whenErrorState_showErrorMessage(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                state = Result.Error(RuntimeException("An error ocurred")),
                onClick = {}
            )
        }

        onNodeWithText("An error ocurred").assertExists()
    }

    @Test
    fun whenSuccessState_showMovies(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                state = Result.Success(generateListMovies(1,2,3)),
                onClick = {}
            )
        }

        onNodeWithText("Title 2").assertExists()
    }

    @Test
    fun whenMovieClicked_showDetail(): Unit = with(composeTestRule) {
        var movieClicked = -1
        val movies = generateListMovies(1,2,3)
        setContent {
            HomeScreen(
                state = Result.Success(movies),
                onClick = { movieClicked = it.id }
            )
        }

        onNodeWithText("Title 2").performClick()

        assertEquals(2, movieClicked)
    }
}