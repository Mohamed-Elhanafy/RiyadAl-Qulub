package com.example.riyadal_qulub.entity

data class WeekDayItem(
    val id: Int,
    val day: String,
    var isDone: Boolean,
    var isToday: Boolean = false,
    var isActive: Boolean = true
)
