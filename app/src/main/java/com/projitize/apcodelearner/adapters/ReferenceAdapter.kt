package com.projitize.apcodelearner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projitize.apcodelearner.databinding.ReferenceItemBinding
import com.projitize.apcodelearner.models.ReferenceModel

class ReferenceAdapter(val callback: (Int, ReferenceItemBinding, ReferenceModel)->Unit) : ListAdapter<ReferenceModel, ReferenceAdapter.ViewHolder>(
    TaskDiffUtil()
){

    class ViewHolder(val binding: ReferenceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ref: ReferenceModel) {
            binding.ref = ref
        }
    }

    class TaskDiffUtil : DiffUtil.ItemCallback<ReferenceModel>() {
        override fun areItemsTheSame(oldItem: ReferenceModel, newItem: ReferenceModel): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: ReferenceModel, newItem: ReferenceModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReferenceItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        callback(position,holder.binding,user)

        holder.bind(user)

        holder.binding.refCount.text = "${position+1}. "


    }
}