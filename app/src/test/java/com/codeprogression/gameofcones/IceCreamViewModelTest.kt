package com.codeprogression.gameofcones

import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by richardcirerol on 11/2/17.
 */
class IceCreamViewModelTest{

    val context = mock<Context>()
    val fakeIceCream = IceCream("Vanilla", "http://www.icecream.com", 120)

    @Test
    fun testTitle() {
        val viewModel = IceCreamViewModel(context, fakeIceCream)
        val expected = "Vanilla"
        val actual = viewModel.title()

        assertEquals(expected, actual)
    }
}