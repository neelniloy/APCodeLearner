package com.projitize.apcodelearner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projitize.apcodelearner.databinding.MiniProjectItemBinding
import com.projitize.apcodelearner.databinding.QaItemBinding
import com.projitize.apcodelearner.databinding.ReferenceItemBinding
import com.projitize.apcodelearner.models.MiniProjectModel
import com.projitize.apcodelearner.models.QaModel
import com.projitize.apcodelearner.models.ReferenceModel

class MiniProjectAdapter(val callback: (Int, MiniProjectItemBinding, MiniProjectModel)->Unit) : ListAdapter<MiniProjectModel, MiniProjectAdapter.ViewHolder>(
    TaskDiffUtil()
){

    class ViewHolder(val binding: MiniProjectItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(project: MiniProjectModel) {
            binding.project = project
        }
    }

    class TaskDiffUtil : DiffUtil.ItemCallback<MiniProjectModel>() {
        override fun areItemsTheSame(oldItem: MiniProjectModel, newItem: MiniProjectModel): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: MiniProjectModel, newItem: MiniProjectModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MiniProjectItemBinding.inflate(
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