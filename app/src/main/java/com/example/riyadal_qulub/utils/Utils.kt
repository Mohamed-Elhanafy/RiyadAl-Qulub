package com.example.riyadal_qulub.utils

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.riyadal_qulub.entity.WeekDayItem
import com.example.riyadal_qulub.entity.WirdStatus
import java.text.SimpleDateFormat
import java.time.LocalDate
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

fun getNextSevenDays(): List<WeekDayItem> {
    val calendar = Calendar.getInstance()
    val arabicLocale = Locale("ar", "SA") // Use Arabic locale
    val dateFormat = SimpleDateFormat("EEEE", arabicLocale)
    val dateNumbers = mutableListOf<WeekDayItem>()

    for (i in 0 until 7) {
        val dayOfWeek = dateFormat.format(calendar.time)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val weekDayItem = WeekDayItem(i, dayOfWeek, WirdStatus.NotDone)
        dateNumbers.add(weekDayItem)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dateNumbers
}

fun getLastSevenDays():List<WeekDayItem>{
    val calendar = Calendar.getInstance()
    val arabicLocale = Locale("ar", "SA") // Use Arabic locale
    val dateFormat = SimpleDateFormat("EEEE", arabicLocale)
    val dateNumbers = mutableListOf<WeekDayItem>()

    for (i in 0 until 7) {
        val dayOfWeek = dateFormat.format(calendar.time)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val weekDayItem = WeekDayItem(i, dayOfWeek, WirdStatus.NotDone)
        dateNumbers.add(weekDayItem)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
    }

    return dateNumbers
}

fun getCurrentDay(): String {
    val calendar = Calendar.getInstance()
    val arabicLocale = Locale("ar", "SA") // Use Arabic locale
    val dateFormat = SimpleDateFormat("EEEE", arabicLocale)
    return dateFormat.format(calendar.time)
}

fun getCurrentDate():String {
    val calendar = Calendar.getInstance()
    val arabicLocale = Locale("ar", "SA") // Use Arabic locale
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", arabicLocale)
    return dateFormat.format(calendar.time)
}

//convert list of getCurrentDate() to list of Date
fun convertListOfStringDatesToListOfDates(listOfDates: List<String>): List<Date> {
    val listOfDatesAsDates = mutableListOf<Date>()
    val arabicLocale = Locale("ar", "SA") // Use Arabic locale
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", arabicLocale)
    for (date in listOfDates) {
        val dateAsDate = dateFormat.parse(date)
        listOfDatesAsDates.add(dateAsDate)
    }
    return listOfDatesAsDates
}

// convert String to date
@RequiresApi(Build.VERSION_CODES.O)
fun convertStringToLocalDate(date: String): LocalDate {
    val arabicLocale = Locale("ar", "SA") // Use Arabic locale
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", arabicLocale)
    val dateAsDate = dateFormat.parse(date)
    val calendar = Calendar.getInstance()
    calendar.time = dateAsDate
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return LocalDate.of(year, month, day)
}
