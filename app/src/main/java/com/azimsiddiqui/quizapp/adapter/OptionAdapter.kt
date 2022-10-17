package com.azimsiddiqui.quizapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azimsiddiqui.quizapp.R
import com.azimsiddiqui.quizapp.databinding.OptionItemBinding
import com.azimsiddiqui.quizapp.model.Question

class OptionAdapter(val context: Context, private val question: Question) :
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    private val optionsList = listOf(question.option1, question.option2, question.option3, question.option4)

    class OptionViewHolder(val binding: OptionItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {

        val binding = OptionItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.binding.quizOption.text = optionsList[position]
        holder.itemView.setOnClickListener {
            question.userAnswer = optionsList[position]
            notifyDataSetChanged()
        }
        if(question.userAnswer == optionsList[position]){
            holder.binding.quizOption.setBackgroundResource(R.drawable.option_item_selected_bg)
        }
        else {
            holder.binding.quizOption.setBackgroundResource(R.drawable.option_item_bg)
        }

    }

    override fun getItemCount(): Int {
        return optionsList.size
    }
}