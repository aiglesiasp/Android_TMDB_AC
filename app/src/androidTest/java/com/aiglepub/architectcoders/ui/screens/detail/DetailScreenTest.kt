package com.aiglepub.architectcoders.ui.screens.detail

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.helpers.generateSampleMovie
import com.aiglepub.architectcoders.ui.common.LOADING_INDICATOR_TAG
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgress(): Unit = with(composeTestRule) {
        setContent {
            DetailScreen(
                state = Result.Loading,
                onBack = {},
                onFavoriteClick = {}
            )
        }
        onNodeWithTag(LOADING_INDICATOR_TAG).assertIsDisplayed()
    }

    @Test
    fun whenErrorState_showErrorMessage(): Unit = with(composeTestRule) {
        setContent {
            DetailScreen(
                state = Result.Error(RuntimeException("An error ocurred")),
                onBack = {},
                onFavoriteClick = {}
            )
        }
        onNodeWithText("An error ocurred").assertExists()
    }

    @Test
    fun whenSuccessState_showMovie(): Unit = with(composeTestRule) {
        setContent {
            DetailScreen(
                state = Result.Success(generateSampleMovie(1)),
                onBack = {},
                onFavoriteClick = {}
            )
        }
        onNodeWithText("Title 1").assertExists()
    }

    @Test
    fun whenFavoriteClick_invokeCallback(): Unit = with(composeTestRule) {
        var clicked = false
        setContent {
            DetailScreen(
                state = Result.Success(generateSampleMovie(1)),
                onBack = {},
                onFavoriteClick = { clicked = true }
            )
        }
        onNodeWithContentDescription("Favorite").performClick()
        assertTrue(clicked)
    }

    @Test
    fun whenBackClick_invokeCallback(): Unit = with(composeTestRule) {
        var clicked = false
        setContent {
            DetailScreen(
                state = Result.Success(generateSampleMovie(1)),
                onBack = { clicked = true },
                onFavoriteClick = {  }
            )
        }
        onNodeWithContentDescription("Arrow back").performClick()
        assertTrue(clicked)
    }
}

private fun getStringResources(@StringRes id: Int): String {
    val ctx = InstrumentationRegistry.getInstrumentation().targetContext
    return ctx.getString(id)
}