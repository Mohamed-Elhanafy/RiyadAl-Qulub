package com.example.riyadal_qulub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.WirdItemBinding
import com.example.riyadal_qulub.entity.DayTask
import com.example.riyadal_qulub.entity.Wird
import com.example.riyadal_qulub.utils.getNextSevenDays

class WirdAdapter : RecyclerView.Adapter<WirdAdapter.WirdViewHolder>() {
    inner class WirdViewHolder(private val binding: WirdItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wird: Wird) {
            binding.apply {
                tvWirdName.text = wird.name
                tvWirdUnit.text = "${wird.quantity} ${wird.unit}"

                val daysAdapter = DaysAdapter()
                rvDaysList.apply {
                    adapter = daysAdapter
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                }
                //todo you need to setting up this list
                daysAdapter.daysDiffer.submitList(getNextSevenDays())


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WirdViewHolder {
        return WirdViewHolder(
            WirdItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: WirdViewHolder, position: Int) {
        val wird = differ.currentList[position]
        holder.bind(wird)
        holder.itemView.setOnClickListener {
            onClick?.invoke(wird)

        }
    }
    var onClick: ((Wird) -> Unit)? = null


}





