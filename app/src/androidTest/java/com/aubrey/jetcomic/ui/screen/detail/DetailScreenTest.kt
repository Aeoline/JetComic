package com.aubrey.jetcomic.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.aubrey.jetcomic.R
import com.aubrey.jetcomic.model.Comic
import com.aubrey.jetcomic.model.OrderComic
import com.aubrey.jetcomic.onNodeWithStringId
import com.aubrey.jetcomic.ui.theme.JetComicTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val fakeOrderReward = OrderComic(
        comic = Comic(
            1,
            "My Hero Academia",
            "Kohei Horikoshi",
            "4.5/5",
            50000,
            "https://readmanga.app/uploads/chapter_files/cover/tbn/1599892837_198x0.jpg",
            "My Hero Academia mengisahkan tentang dunia di mana hampir semua orang memiliki kekuatan khusus yang disebut \"Quirks\". Izuku Midoriya, seorang remaja tanpa Quirk, bermimpi menjadi seorang pahlawan super seperti idolanya, All Might. Suatu hari, dia diberi kesempatan untuk mewujudkan mimpinya ketika dia menerima Quirk dari All Might sendiri. Ikuti perjalanan Izuku dalam menghadapi tantangan sebagai seorang siswa di sekolah pahlawan dan melawan penjahat yang mengancam kedamaian."
        ),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetComicTheme {
                DetailContent(
                    fakeOrderReward.comic.photoUrl,
                    fakeOrderReward.comic.title,
                    fakeOrderReward.comic.author,
                    fakeOrderReward.comic.rating,
                    fakeOrderReward.comic.description,
                    0,
                    fakeOrderReward.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeOrderReward.comic.author)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeOrderReward.comic.rating)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeOrderReward.comic.description)
            .assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun decreaseProduct_stillZero() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}