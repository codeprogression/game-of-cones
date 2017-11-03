package com.codeprogression.gameofcones

import android.content.Context

data class IceCream(val title: String, val iconUrl: String, val calorieCount: Int)

class IceCreamViewModel(private val context: Context, private val iceCream: IceCream) {
    fun title(): String = iceCream.title
    fun iconUrl(): String = iceCream.iconUrl
    fun calorieCount(): String = context.getString(R.string.formatted_calorie_count, iceCream.calorieCount)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IceCreamViewModel) return false

        if (context != other.context) return false
        if (iceCream != other.iceCream) return false

        return true
    }

    override fun hashCode(): Int {
        var result = context.hashCode()
        result = 31 * result + iceCream.hashCode()
        return result
    }

}
