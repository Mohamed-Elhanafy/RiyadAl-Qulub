package com.example.riyadal_qulub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.DaysItemBinding
import com.example.riyadal_qulub.entity.WeekDayItem
import com.example.riyadal_qulub.entity.WirdStatus

class DaysAdapter() :
    RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {
    inner class DaysViewHolder(private val binding: DaysItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: WeekDayItem) {
            binding.apply {
                tvDayName.text = day.day
                when (day.wirdStatus) {
                    WirdStatus.Done -> {
                        tvDayName.background =
                            itemView.resources.getDrawable(R.drawable.calender_day_background)
                        tvDayName.setTextColor(itemView.resources.getColor(R.color.white))
                    }

                    WirdStatus.IsToday -> {
                        tvDayName.background =
                            itemView.resources.getDrawable(R.drawable.calender_day_background_today)
                        tvDayName.setTextColor(itemView.resources.getColor(R.color.white))
                    }

                    WirdStatus.NotDone -> {
                        tvDayName.background =
                            itemView.resources.getDrawable(R.drawable.calender_day_background_not_selected)
                        tvDayName.setTextColor(itemView.resources.getColor(R.color.black))
                    }
                }

            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<WeekDayItem>() {
        override fun areItemsTheSame(oldItem: WeekDayItem, newItem: WeekDayItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeekDayItem, newItem: WeekDayItem): Boolean {
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

            when(day.wirdStatus){
                WirdStatus.Done -> day.wirdStatus = WirdStatus.NotDone
                WirdStatus.IsToday -> WirdStatus.IsToday
                WirdStatus.NotDone -> day.wirdStatus = WirdStatus.Done
            }
            notifyItemChanged(position)
            onClick?.let { it(day) }
        }

    }

    override fun getItemCount(): Int {
        return daysDiffer.currentList.size
    }

    var onClick: ((WeekDayItem) -> Unit)? = null
}