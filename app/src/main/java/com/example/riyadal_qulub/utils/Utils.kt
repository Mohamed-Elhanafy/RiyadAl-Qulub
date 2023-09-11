package com.example.riyadal_qulub.utils

import android.app.Activity

fun onBoardingFinished(activity: Activity){
    val sharedPref = activity?.getSharedPreferences(PreferenceKeys.boardingSharedPref, 0)
    val editor = sharedPref?.edit()
    editor?.putBoolean(PreferenceKeys.boardingFinished, true)
    editor?.apply()
}