package com.azimsiddiqui.quizapp.util

object ColorPicker {

    private val colorList =
        listOf(
            "#E91E63",
            "#FFC107",
            "#3F51B5",
            "#795548",
            "#9C27B0",
            "#8BC34A",
            "#FBC02D",
            "#CFD8DC"
        )

    fun getRandomColor(): String {

        return colorList.random()
    }
}