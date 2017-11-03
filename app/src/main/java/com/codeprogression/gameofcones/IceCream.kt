package com.codeprogression.gameofcones

import android.content.Context

data class IceCream(val title: String, val iconUrl: String, val calorieCount: Int)

class IceCreamViewModel(val context: Context, val iceCream: IceCream) {
    fun title(): String = iceCream.title
    fun iconUrl(): String = iceCream.iconUrl
    fun calorieCount(): String = context.getString(R.string.formatted_calorie_count, iceCream.calorieCount)
}
