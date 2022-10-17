package com.azimsiddiqui.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.azimsiddiqui.quizapp.adapter.QuizAdapter
import com.azimsiddiqui.quizapp.databinding.ActivityMainBinding
import com.azimsiddiqui.quizapp.model.Quiz
import com.azimsiddiqui.quizapp.util.formattedDate
import com.azimsiddiqui.quizapp.util.showToast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: QuizAdapter
    private var quizList: MutableList<Quiz> = mutableListOf()
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        setupFireStore()
        setUpViews()

    }

    private fun setupFireStore() {
        val collectionReference = firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->

            if (value == null || error != null) {
                showToast(error.toString())
                return@addSnapshotListener
            }
            quizList.clear()
            quizList.addAll(value!!.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpViews() {
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        binding.fabDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener { date ->
                val formattedDate = formattedDate("dd-mm-yyyy", date = date)
                filterQuizBasedOnDate(formattedDate)
            }
            datePicker.addOnNegativeButtonClickListener {
            }

            datePicker.addOnCancelListener {
            }
        }
    }

    private fun filterQuizBasedOnDate(date: String) {
        val collectionReference = firestore.collection("quizzes").whereEqualTo("date", date)
        collectionReference.addSnapshotListener { value, error ->

            if (value == null || error != null) {
                showToast(error.toString())
                return@addSnapshotListener
            }
            quizList.clear()
            quizList.addAll(value!!.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this, quizList)
        binding.quizRecyclerview.layoutManager = GridLayoutManager(this, 2)
        binding.quizRecyclerview.adapter = adapter
    }

    private fun setUpDrawerLayout() {
        setSupportActionBar(binding.topAppBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.mainDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()

        binding.navView.setNavigationItemSelectedListener {

            when(it.itemId) {
                R.id.item_profile -> {
                    showToast("profile")
                    true
                }
                R.id.item_result -> {
                    showToast("result")
                    true
                }
                R.id.item_follow -> {
                    showToast("follow us")
                    true
                }
                R.id.logout -> {
                    logout()
                    showToast("you have been logged out!")
                    true
                }
                else -> {
                    true
                }

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        when (item.itemId) {
            R.id.item_logout -> {
                showToast("logout")
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}