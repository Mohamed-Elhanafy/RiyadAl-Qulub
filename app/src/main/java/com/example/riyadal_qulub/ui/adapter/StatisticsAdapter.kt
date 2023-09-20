package com.example.riyadal_qulub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.riyadal_qulub.databinding.ItemStatBinding
import com.example.riyadal_qulub.entity.Wird

class StatisticsAdapter :
    RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder>() {
        inner class StatisticsViewHolder(private val binding: ItemStatBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(wird: Wird) {
                binding.apply {
                    wirdName.text = wird.name
                    if (wird.unit != "") {
                        tvTotalTimes.text = "إجمالي إنجاز الورد  "
                        tvQuantity.text = (wird.doneDays.size * wird.quantity).toString() + " " + wird.unit
                    } else {
                        tvTotalTimes.text = " إجمالي المرات "
                        tvQuantity.text = wird.doneDays.size.toString()
                    }
                }
            }
        }

        private val diffCallback = object : DiffUtil.ItemCallback<Wird>() {
            override fun areItemsTheSame(oldItem: Wird, newItem: Wird): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Wird, newItem: Wird): Boolean {
                return oldItem.id == newItem.id
            }

        }
        val differ = AsyncListDiffer(this, diffCallback)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsAdapter.StatisticsViewHolder {
            return StatisticsViewHolder(
                ItemStatBinding.inflate(
                    LayoutInflater.from(parent.context),parent, false
                )
            )
        }

        override fun onBindViewHolder(holder: StatisticsAdapter.StatisticsViewHolder, position: Int) {
            val day = differ.currentList[position]
            holder.bind(day)
            holder.itemView.setOnClickListener {
                onClick?.let { it(day) }
            }

        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        var onClick: ((Wird) -> Unit)? = null
}