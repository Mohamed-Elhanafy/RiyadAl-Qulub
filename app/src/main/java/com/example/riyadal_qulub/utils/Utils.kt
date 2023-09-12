package com.example.riyadal_qulub.utils

import android.app.Activity
import java.text.SimpleDateFormat
import java.util.Date

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