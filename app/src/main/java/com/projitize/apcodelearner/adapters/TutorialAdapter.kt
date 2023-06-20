package com.projitize.apcodelearner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projitize.apcodelearner.databinding.MiniProjectItemBinding
import com.projitize.apcodelearner.databinding.QaItemBinding
import com.projitize.apcodelearner.databinding.ReferenceItemBinding
import com.projitize.apcodelearner.databinding.TutorialItemBinding
import com.projitize.apcodelearner.models.MiniProjectModel
import com.projitize.apcodelearner.models.QaModel
import com.projitize.apcodelearner.models.ReferenceModel
import com.projitize.apcodelearner.models.TutorialModel

class TutorialAdapter(val callback: (Int, TutorialItemBinding, TutorialModel)->Unit) : ListAdapter<TutorialModel, TutorialAdapter.ViewHolder>(
    TaskDiffUtil()
){

    class ViewHolder(val binding: TutorialItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tutorial: TutorialModel) {
            binding.tutorial = tutorial
        }
    }

    class TaskDiffUtil : DiffUtil.ItemCallback<TutorialModel>() {
        override fun areItemsTheSame(oldItem: TutorialModel, newItem: TutorialModel): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: TutorialModel, newItem: TutorialModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TutorialItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        callback(position,holder.binding,user)

        holder.bind(user)

        holder.binding.count.text = "${position+1}."


    }
}