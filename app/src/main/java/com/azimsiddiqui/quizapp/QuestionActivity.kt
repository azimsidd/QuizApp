package com.azimsiddiqui.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.azimsiddiqui.quizapp.adapter.OptionAdapter
import com.azimsiddiqui.quizapp.databinding.ActivityQuestionBinding
import com.azimsiddiqui.quizapp.model.Question
import com.azimsiddiqui.quizapp.model.Quiz
import com.azimsiddiqui.quizapp.util.showToast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private lateinit var firestore: FirebaseFirestore
    private var quizList: MutableList<Quiz> = mutableListOf()
    private var questions: MutableMap<String, Question> = mutableMapOf()
    var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFireStore()

        setupButtonClick()

    }

    private fun setupButtonClick() {
        binding.btnPrev.setOnClickListener {
            index--
            setUpView()
        }
        binding.btnNext.setOnClickListener {
            when (binding.btnNext.text) {
                ButtonState.NEXT.type -> {
                    index++
                    setUpView()
                }
                ButtonState.SUBMIT.type -> {
                    val intent = Intent(this, ResultActivity::class.java)
                    val json = Gson().toJson(quizList[0])
                    intent.putExtra("QUIZ", json)
                    startActivity(intent)
                    Log.d("Button", "start activity")
                }
            }
        }
    }

    private fun setupFireStore() {
        val date = intent.getStringExtra("DATE")
        firestore = FirebaseFirestore.getInstance()
        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("date", date)
                .get()
                .addOnSuccessListener {
                    if (!it.isEmpty && it != null) {
                        quizList = it.toObjects(Quiz::class.java)
                        questions = quizList[0].questions
                        setUpView()
                    }
                }
        }
    }

    private fun setUpView() {
        binding.btnNext.visibility = View.GONE
        binding.btnPrev.visibility = View.GONE

        when (index) {
            questions.size -> {
                binding.btnNext.text = "SUBMIT"
                binding.btnNext.visibility = View.VISIBLE
            }
            in 2..questions.size -> {
                binding.btnNext.text = "SUBMIT"
                binding.btnNext.visibility = View.VISIBLE
                binding.btnPrev.visibility = View.VISIBLE
            }
            1 -> {
                binding.btnNext.visibility = View.VISIBLE
                binding.btnNext.text = "NEXT"
            }
            else -> {
                binding.btnNext.text = "NEXT"
                binding.btnNext.visibility = View.VISIBLE
                binding.btnPrev.visibility = View.VISIBLE
            }
        }

        val question = questions["question${index}"]
        question?.let {
            binding.tvQuestion.text = it.description
            setUpOptionRecyclerview(it)
        }
    }

    private fun setUpOptionRecyclerview(question: Question) {
        binding.rvOptions.adapter = OptionAdapter(this, question)
        binding.rvOptions.layoutManager = LinearLayoutManager(this)
        binding.rvOptions.setHasFixedSize(true)
    }
}


enum class ButtonState(val type: String) {
    NEXT("NEXT"),
    SUBMIT("SUBMIT")
}

