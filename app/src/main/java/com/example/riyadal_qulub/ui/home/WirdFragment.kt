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
import com.example.riyadal_qulub.entity.Wird
import com.example.riyadal_qulub.utils.convertStringToLocalDate
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

            setUpCardView(wird)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun FragmentWirdBinding.setUpCardView(wird: Wird) {
        val listOfDoneDays = wird.doneDays
        val monthCounter = mutableListOf<Int>()
        listOfDoneDays.forEach { doneDate ->
            val date = convertStringToLocalDate(doneDate)
            val day = date.dayOfMonth
            val month = date.monthValue
            val year = date.year
            val dateText = "$day/$month/$year"

            //check the donedates in this mouth
            if (date.monthValue == LocalDate.now().monthValue) {
                Log.i(TAG, "setUpCardView: $dateText")
                monthCounter.add(day)
            }

        }

        binding.tvMonthlyCount.text = monthCounter.size.toString()
        binding.tvTotalCount.text = listOfDoneDays.size.toString()
        val totalPercentage = ((monthCounter.size.toFloat() / LocalDate.now().lengthOfMonth()
            .toFloat()) * 100).toInt()

        binding.tvMonthPrcCount.text = "$totalPercentage %"

        //check if there is strike in the wird and count it
        var strikeCounter = 0

        for (i in 0 until monthCounter.size) {
            if (i == 0) {
                strikeCounter++
            } else {
                if (monthCounter[i] - monthCounter[i - 1] == 1) {
                    strikeCounter++
                }
            }
        }
        binding.tvStrikes.text = strikeCounter.toString()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun FragmentWirdBinding.setUpCalender() {
        class DayViewContainer(view: View) : ViewContainer(view) {

            val textView = CalendarDayLayoutBinding.bind(view).calendarDayText

            init {
                view.setOnClickListener {
                    Log.i(TAG, textView.text.toString())

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

                val wird = args.wird
                val listOfDoneDays = wird.doneDays
                val newList = mutableListOf<LocalDate>()



                if (data.position == DayPosition.MonthDate) {
                    container.textView.setTextColor(Color.BLACK)
                } else {
                    container.textView.setTextColor(resources.getColor(R.color.main_color_light))
                }

                if (data.date == LocalDate.now() && !wird.isDone) {
                    container.textView.setBackgroundResource(R.drawable.calender_day_background_today)
                } else if (data.date == LocalDate.now() && wird.isDone) {
                    container.textView.setBackgroundResource(R.drawable.calender_day_background)
                    container.textView.setTextColor(Color.WHITE)
                }
                listOfDoneDays.forEach {
                    newList.add(convertStringToLocalDate(it))
                    if (data.date == convertStringToLocalDate(it)) {
                        container.textView.setBackgroundResource(R.drawable.calender_day_background)
                        container.textView.setTextColor(Color.WHITE)
                    }

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
                    DayOfWeek.SUNDAY,
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY,
                    DayOfWeek.SATURDAY
                )
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ar"))
                child.text = title
            }
        }
    }


}