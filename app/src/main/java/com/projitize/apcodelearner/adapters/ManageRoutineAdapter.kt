package com.braineer.scheduler.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.braineer.scheduler.databinding.ManageRoutineItemBinding

class ManageRoutineAdapter(val editCallback: (Routine) -> Unit,val deleteCallback: (Routine) -> Unit) : ListAdapter<Routine, ManageRoutineAdapter.RoutineViewHolder>(
    RoutineDiffUtil()
){

    class RoutineViewHolder(val binding: ManageRoutineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(routine: Routine) {
            binding.routine = routine
        }
    }

    class RoutineDiffUtil : DiffUtil.ItemCallback<Routine>() {
        override fun areItemsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem.routineId == newItem.routineId
        }

        override fun areContentsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = ManageRoutineItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = getItem(position)

        holder.bind(routine)
        holder.itemView.setOnClickListener {

        }

        holder.binding.edit.setOnClickListener{
            editCallback(routine)
        }

        holder.binding.delete.setOnClickListener{
            deleteCallback(routine)
        }

    }
}