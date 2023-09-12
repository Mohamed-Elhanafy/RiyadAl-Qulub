package com.example.riyadal_qulub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.DaysItemBinding
import com.example.riyadal_qulub.entity.DayTask

class DaysAdapter() :
    RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {
    inner class DaysViewHolder(private val binding: DaysItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: DayTask) {
            binding.apply {
                tvDayName.text = day.day
                tvDayNumber.text = day.dayMonth.toString()
                if (day.isDone) {
                    tvDayNumber.background =
                        itemView.resources.getDrawable(R.drawable.calender_day_background)
                    tvDayNumber.setTextColor(itemView.resources.getColor(R.color.white))
                } else {
                    tvDayNumber.background =
                        itemView.resources.getDrawable(R.drawable.calender_day_background_not_selected)
                    tvDayNumber.setTextColor(itemView.resources.getColor(R.color.black))
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<DayTask>() {
        override fun areItemsTheSame(oldItem: DayTask, newItem: DayTask): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DayTask, newItem: DayTask): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val daysDiffer = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysAdapter.DaysViewHolder {
        return DaysViewHolder(
            DaysItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: DaysAdapter.DaysViewHolder, position: Int) {
        val day = daysDiffer.currentList[position]
        holder.bind(day)
        holder.itemView.setOnClickListener {
            day.isDone = !day.isDone
            notifyItemChanged(position)
            onClick?.let { it(day) }
        }

    }

    override fun getItemCount(): Int {
        return daysDiffer.currentList.size
    }

    var onClick: ((DayTask) -> Unit)? = null
}