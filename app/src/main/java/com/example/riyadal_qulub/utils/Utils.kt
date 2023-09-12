package com.example.riyadal_qulub.utils

import android.app.Activity
import com.example.riyadal_qulub.entity.DayTask
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun onBoardingFinished(activity: Activity){
    val sharedPref = activity?.getSharedPreferences(PreferenceKeys.boardingSharedPref, 0)
    val editor = sharedPref?.edit()
    editor?.putBoolean(PreferenceKeys.boardingFinished, true)
    editor?.apply()
}


fun getCurrentTimeStamp(): String? {
    return try {
        //Dec 1 2024
        val dateFormat: SimpleDateFormat = SimpleDateFormat("MMM d yyyy")
        dateFormat.format(Date())
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getNextSevenDays(): List<DayTask> {
    val calendar = Calendar.getInstance()
    val arabicLocale = Locale("ar", "SA") // Use Arabic locale
    val dateFormat = SimpleDateFormat("EEEE", arabicLocale)
    val dateNumbers = mutableListOf<DayTask>()

    for (i in 0 until 7) {
        val dayOfWeek = dateFormat.format(calendar.time)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayTask = DayTask(i, dayOfWeek, false, dayOfMonth)
        dateNumbers.add(dayTask)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dateNumbers
}
