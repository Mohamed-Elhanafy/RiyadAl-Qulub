package com.example.riyadal_qulub.ui.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs


import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.CalendarDayLayoutBinding
import com.example.riyadal_qulub.databinding.FragmentWirdBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private const val TAG = "WirdFragment"


class WirdFragment : Fragment() {
    lateinit var binding: FragmentWirdBinding
    private val args by navArgs<WirdFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWirdBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wird = args.wird
        Log.i(TAG, "wird is : ${wird.name} ")
        binding.apply {
            tvWirdName.text = wird.name

            setUpCalender()

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun FragmentWirdBinding.setUpCalender() {
        class DayViewContainer(view: View) : ViewContainer(view) {

            val textView = CalendarDayLayoutBinding.bind(view).calendarDayText

            init {
                view.setOnClickListener {
                    Log.i(TAG, "setUpCalender: ")
                }
            }
        }
        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
                if (data.position == DayPosition.MonthDate) {
                    container.textView.setTextColor(Color.BLACK)
                } else {
                    container.textView.setTextColor(Color.GRAY)
                }
                if (data.date == LocalDate.now()) {
                    container.textView.setBackgroundResource(R.drawable.calender_day_background_today)
                }
            }
        }
        val currentMonth = YearMonth.now()

        val startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        calendarView.setup(startMonth, endMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        val titlesContainer = binding.root.findViewById<ViewGroup>(R.id.titlesContainer)
        for (index in 0 until titlesContainer.childCount) {
            val child = titlesContainer.getChildAt(index)
            if (child is TextView) {
                val daysOfWeek = listOf(
                    DayOfWeek.SATURDAY,
                    DayOfWeek.SUNDAY,
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
                )
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ar"))
                child.text = title
            }
        }
    }


}