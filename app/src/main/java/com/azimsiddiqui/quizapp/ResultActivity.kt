package com.azimsiddiqui.quizapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.azimsiddiqui.quizapp.databinding.ActivityMainBinding
import com.azimsiddiqui.quizapp.databinding.ActivityResultBinding
import com.azimsiddiqui.quizapp.model.Quiz
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var quiz: Quiz
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson(quizData, Quiz::class.java)

        calculateScore()
        setUpAnswerView()
    }

    private fun setUpAnswerView() {

        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvAnswerView.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            binding.tvAnswerView.text = Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        var score = 0

        for (entry in quiz.questions.entries) {
            val question = entry.value
            if (question.userAnswer == question.answer) {
                score += 10
            }
        }
        binding.tvScore.text = "Your score is: $score "
    }
}