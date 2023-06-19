package com.projitize.apcodelearner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projitize.apcodelearner.databinding.FeedbackItemBinding
import com.projitize.apcodelearner.databinding.QaItemBinding
import com.projitize.apcodelearner.databinding.ReferenceItemBinding
import com.projitize.apcodelearner.models.FeedbackModel
import com.projitize.apcodelearner.models.QaModel
import com.projitize.apcodelearner.models.ReferenceModel

class FeedbackAdapter(val callback: (Int, FeedbackItemBinding, FeedbackModel)->Unit) : ListAdapter<FeedbackModel, FeedbackAdapter.ViewHolder>(
    TaskDiffUtil()
){

    class ViewHolder(val binding: FeedbackItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feedback: FeedbackModel) {
            binding.feedback = feedback
        }
    }

    class TaskDiffUtil : DiffUtil.ItemCallback<FeedbackModel>() {
        override fun areItemsTheSame(oldItem: FeedbackModel, newItem: FeedbackModel): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: FeedbackModel, newItem: FeedbackModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FeedbackItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        callback(position,holder.binding,user)

        holder.bind(user)


    }
}