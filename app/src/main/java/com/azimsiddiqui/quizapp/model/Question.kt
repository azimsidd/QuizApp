package com.azimsiddiqui.quizapp.model

data class Question(
    var description: String = "",
    val option1: String = "",
    val option2: String = "",
    val option3: String = "",
    val option4: String = "",
    var answer: String = "",
    var userAnswer: String = ""
)
