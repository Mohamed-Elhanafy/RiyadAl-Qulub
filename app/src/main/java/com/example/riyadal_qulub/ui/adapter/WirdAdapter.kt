package com.example.riyadal_qulub.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.WirdItemBinding
import com.example.riyadal_qulub.entity.WeekDayItem
import com.example.riyadal_qulub.entity.Wird
import com.example.riyadal_qulub.entity.WirdStatus
import com.example.riyadal_qulub.utils.getCurrentDate
import com.example.riyadal_qulub.utils.getLastSevenDays
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//implement long press click listener
class WirdAdapter() : RecyclerView.Adapter<WirdAdapter.WirdViewHolder>() {
    private var onItemClick: ((Wird) -> Unit)? = null
    private var onDayClick: ((WeekDayItem, Wird) -> Unit)? = null
    var onClick: ((Wird) -> Unit)? = null
    var onLongClick: ((Wird) -> Unit)? = null


    fun setOnButtonClickListener(listener: (Wird) -> Unit) {
        onItemClick = listener
    }

    fun setOnDayClickListener(listener: (WeekDayItem, Wird) -> Unit) {
        onDayClick = listener
    }

    inner class WirdViewHolder(private val binding: WirdItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wird: Wird) {
            val list = getLastSevenDaysFromWird(wird)
            val daysAdapter = HomeDaysAdapter(list)

            binding.rvDaysList.apply {
                adapter = daysAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            }

            binding.apply {
                tvWirdName.text = wird.name
                if (wird.quantity != 0) {
                    tvWirdUnit.text = "${wird.quantity} ${wird.unit}"
                }

                btnDone.setOnClickListener {
                    onItemClick?.invoke(wird)
                    notifyDataSetChanged()
                    daysAdapter.notifyDataSetChanged()
                }

            }

            daysAdapter.onClick = {
                onDayClick?.invoke(it, wird)
                Log.i("TAG", "bind: ${it.id}")
            }

            when (wird.doneDays.contains(getCurrentDate())) {
                true -> {
                    binding.btnDone.icon =
                        getDrawable(binding.root.context, R.drawable.ic_refresh)
                }

                false -> {
                    binding.btnDone.icon =
                        getDrawable(binding.root.context, R.drawable.ic_check)
                }
            }

        }

        private fun getLastSevenDaysFromWird(wird: Wird): List<WeekDayItem> {

            val calendar = Calendar.getInstance()
            val arabicLocale = Locale("ar", "SA") // Use Arabic locale
            val dateFormat = SimpleDateFormat("EEEE", arabicLocale)
            val date2Format = SimpleDateFormat("dd MMMM yyyy", arabicLocale)
            val dateNumbers = mutableListOf<WeekDayItem>()
            //check is the day is in the list of wird.wirdDays and add it to the list if it's not there and change the isActive to true
            //if it's there change the isActive to false
            for (i in 0 until 7) {
                val dayOfWeek = dateFormat.format(calendar.time)
                val date = date2Format.format(calendar.time)

                if (wird.doneDays.contains(date.toString())) {
                    val weekDayItem = WeekDayItem(i + wird.id, dayOfWeek, WirdStatus.Done)
                    dateNumbers.add(weekDayItem)
                } else if (getCurrentDate() == date.toString()) {
                    val weekDayItem = WeekDayItem(i + wird.id, dayOfWeek, WirdStatus.IsToday)
                    dateNumbers.add(weekDayItem)
                } else {
                    val weekDayItem = WeekDayItem(i + wird.id, dayOfWeek, WirdStatus.NotDone)
                    dateNumbers.add(weekDayItem)
                }

                calendar.add(Calendar.DAY_OF_MONTH, -1)
            }
            return dateNumbers
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
                LayoutInflater.from(parent.context), parent, false
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
        holder.itemView.setOnLongClickListener {
            onLongClick?.invoke(wird)
            true
        }
    }


}








