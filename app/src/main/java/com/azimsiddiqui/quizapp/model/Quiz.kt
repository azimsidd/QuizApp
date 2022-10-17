package com.azimsiddiqui.quizapp.model

data class Quiz(
    var id: String = "",
    var date: String = "",
    val questions: MutableMap<String, Question> = mutableMapOf()
)
