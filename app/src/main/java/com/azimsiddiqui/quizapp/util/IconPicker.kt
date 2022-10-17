package com.azimsiddiqui.quizapp.util

import com.azimsiddiqui.quizapp.R

object IconPicker {

    private val iconsList =
        listOf(
            R.drawable.ic_abacus,
            R.drawable.ic_book_puzzle,
            R.drawable.ic_chemistry_liquid_icon,
            R.drawable.ic_knowledge_book,
            R.drawable.ic_calculator_math,
            R.drawable.ic_code_education,
            R.drawable.ic_graduation
        )

    fun getRandomIcon(): Int {
        return iconsList.random()
    }
}