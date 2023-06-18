package com.projitize.apcodelearner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projitize.apcodelearner.databinding.QaItemBinding
import com.projitize.apcodelearner.databinding.ReferenceItemBinding
import com.projitize.apcodelearner.models.QaModel
import com.projitize.apcodelearner.models.ReferenceModel

class QAAdapter(val callback: (Int, QaItemBinding, QaModel)->Unit) : ListAdapter<QaModel, QAAdapter.ViewHolder>(
    TaskDiffUtil()
){

    class ViewHolder(val binding: QaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(qa: QaModel) {
            binding.qa = qa
        }
    }

    class TaskDiffUtil : DiffUtil.ItemCallback<QaModel>() {
        override fun areItemsTheSame(oldItem: QaModel, newItem: QaModel): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: QaModel, newItem: QaModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QaItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        callback(position,holder.binding,user)

        holder.bind(user)

        holder.binding.refCount.text = "Q${position+1}. "


    }
}