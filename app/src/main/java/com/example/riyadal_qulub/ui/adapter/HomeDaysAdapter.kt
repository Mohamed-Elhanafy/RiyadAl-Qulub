package com.example.riyadal_qulub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.DaysItemBinding
import com.example.riyadal_qulub.entity.WeekDayItem

class HomeDaysAdapter(var daysList: List<WeekDayItem>) :
    RecyclerView.Adapter<HomeDaysAdapter.HomeDaysViewHolder>() {
    var list: List<WeekDayItem> = ArrayList()

    init {
        list = daysList
    }

    inner class HomeDaysViewHolder(private val binding: DaysItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: WeekDayItem) {
            binding.apply {
                tvDayName.text = day.day

                if (day.isToday && !day.isDone) {
                    tvDayName.background =
                        itemView.resources.getDrawable(R.drawable.calender_day_background_today)
                    tvDayName.setTextColor(itemView.resources.getColor(R.color.white))
                } else
                    if (day.isDone) {
                        tvDayName.background =
                            itemView.resources.getDrawable(R.drawable.calender_day_background)
                        tvDayName.setTextColor(itemView.resources.getColor(R.color.white))
                    } else {
                        tvDayName.background =
                            itemView.resources.getDrawable(R.drawable.calender_day_background_not_selected)
                        tvDayName.setTextColor(itemView.resources.getColor(R.color.black))
                    }

            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeDaysAdapter.HomeDaysViewHolder {
        return HomeDaysViewHolder(
            DaysItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: HomeDaysAdapter.HomeDaysViewHolder, position: Int) {
        val day = list[position]
        holder.bind(day)
        holder.itemView.setOnClickListener {
            day.isDone = !day.isDone
            notifyItemChanged(position)
            onClick?.let { it(day) }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    var onClick: ((WeekDayItem) -> Unit)? = null
}
