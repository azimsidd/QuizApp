package com.azimsiddiqui.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.azimsiddiqui.quizapp.databinding.ActivityLoginBinding
import com.azimsiddiqui.quizapp.databinding.ActivityLoginIntroBinding
import com.azimsiddiqui.quizapp.util.showToast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.tvHavntAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }


    private fun login() {
        val email = binding.tvEmail.text.toString()
        val password = binding.tvPassword.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {

            if(it.isSuccessful) {
                showToast("User Logged in success")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                showToast("Authentication failed")
            }
        }

    }
}