package com.azimsiddiqui.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.azimsiddiqui.quizapp.databinding.ActivityLoginIntroBinding
import com.azimsiddiqui.quizapp.util.ScreenType
import com.azimsiddiqui.quizapp.util.showToast
import com.google.firebase.auth.FirebaseAuth

class LoginIntro : AppCompatActivity() {

    lateinit var binding: ActivityLoginIntroBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null) {
            showToast("User Already logged in")
            redirect(ScreenType.MAIN)
        }

        binding.btnGetStarted.setOnClickListener {
            redirect(ScreenType.LOGIN)
        }

    }

    private fun redirect(screen: ScreenType) {
        val intent = when(screen){
            ScreenType.LOGIN -> Intent(this, LoginActivity::class.java)
            ScreenType.MAIN -> Intent(this, MainActivity::class.java)
            else -> throw Exception()
        }
        startActivity(intent)
        finish()
    }
}