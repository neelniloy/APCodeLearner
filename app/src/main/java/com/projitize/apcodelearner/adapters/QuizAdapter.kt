package com.projitize.apcodelearner.adapters

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projitize.apcodelearner.databinding.QaItemBinding
import com.projitize.apcodelearner.databinding.QuizItemBinding
import com.projitize.apcodelearner.databinding.ReferenceItemBinding
import com.projitize.apcodelearner.models.QaModel
import com.projitize.apcodelearner.models.QuizModel
import com.projitize.apcodelearner.models.ReferenceModel

private val selectedIndices = SparseArray<Int>()

class QuizAdapter(val callback: (Int, QuizItemBinding, QuizModel)->Unit) : ListAdapter<QuizModel, QuizAdapter.ViewHolder>(
    TaskDiffUtil()
){

    class ViewHolder(val binding: QuizItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: QuizModel) {
            binding.quiz = quiz
        }
    }

    class TaskDiffUtil : DiffUtil.ItemCallback<QuizModel>() {
        override fun areItemsTheSame(oldItem: QuizModel, newItem: QuizModel): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: QuizModel, newItem: QuizModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QuizItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        callback(position,holder.binding,user)

        holder.bind(user)

        holder.binding.refCount.text = "Q${position+1}. "

        // Set the selected index for this item
        val selectedIndex = selectedIndices.get(position, -1)
        if (selectedIndex != -1) {
            holder.binding.radioGroup.check(holder.binding.radioGroup.getChildAt(selectedIndex).id)
        } else {
            holder.binding.radioGroup.clearCheck()
        }

        // Set the listener for radio button selection
        holder.binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
            val selectedIndex = group.indexOfChild(selectedRadioButton)

            // Update the selected index for this item
            selectedIndices.put(position, selectedIndex)
            notifyItemChanged(position)

        }
    }
}