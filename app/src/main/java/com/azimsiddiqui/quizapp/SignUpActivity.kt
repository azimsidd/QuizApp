package com.azimsiddiqui.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.azimsiddiqui.quizapp.databinding.ActivitySignUpBinding
import com.azimsiddiqui.quizapp.util.showToast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            signUpUser()
        }
        binding.tvAlreadyAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    private fun signUpUser() {
        val email = binding.tvEmail.text.toString()
        val password = binding.tvPassword.text.toString()
        val confirmPassword = binding.tvConfirmPassword.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            showToast("Email and password cant be blank")
            return
        }
        if (password != confirmPassword) {
            showToast("password and confirm password do not match")
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                showToast("Account created successfully")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                showToast(it.exception.toString())
            }
        }
    }
}