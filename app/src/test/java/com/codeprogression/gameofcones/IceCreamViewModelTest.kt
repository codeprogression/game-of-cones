package com.codeprogression.gameofcones

import android.content.Context
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
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

    @Test
    fun testIconUrl() {
        val viewModel = IceCreamViewModel(context, fakeIceCream)
        val expected = "http://www.icecream.com"
        val actual = viewModel.iconUrl()

        assertEquals(expected, actual)
    }

    @Test
    fun testCalorieCount() {
        whenever(context.getString(any(), any())).thenReturn("120 Calories")
        IceCreamViewModel(context, fakeIceCream).calorieCount()
        verify(context).getString(R.string.formatted_calorie_count, 120)
    }
}