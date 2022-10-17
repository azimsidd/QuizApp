package com.azimsiddiqui.quizapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.azimsiddiqui.quizapp.QuestionActivity
import com.azimsiddiqui.quizapp.R
import com.azimsiddiqui.quizapp.databinding.QuizItemBinding
import com.azimsiddiqui.quizapp.model.Quiz
import com.azimsiddiqui.quizapp.util.ColorPicker
import com.azimsiddiqui.quizapp.util.IconPicker

class QuizAdapter(val context: Context, val quizzes: List<Quiz>) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = QuizItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        with(holder) {
            binding.tvDate.text = quizzes[position].date
            binding.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getRandomColor()))
            binding.itemImage.setImageResource(IconPicker.getRandomIcon())
            binding.cardContainer.setOnClickListener {
                val intent = Intent(context, QuestionActivity::class.java)
                intent.putExtra("DATE", quizzes[position].date)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    inner class QuizViewHolder(val binding: QuizItemBinding) : RecyclerView.ViewHolder(binding.root)

}

