package com.example.riyadal_qulub.utils

import android.app.Activity

fun onBoardingFinished(activity: Activity){
    val sharedPref = activity?.getSharedPreferences(Constant.boardingSharedPref, 0)
    val editor = sharedPref?.edit()
    editor?.putBoolean(Constant.boardingFinished, true)
    editor?.apply()
}