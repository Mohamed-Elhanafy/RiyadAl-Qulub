package com.example.riyadal_qulub.entity

import java.util.Date


data class Wird(
    val id: Int,
    val name: String,
    val description: String,
    val count: Int,
    val countDone: Int,
    val isDone: Boolean,
    val isAlarm: Boolean,
    val alarmTime: String,
    val alarmDays: List<Date>,
    val unit: String,
    val quantity: Int,
    val isDaily: Boolean,
    val customDays: String,
    val startDate: String,
    val endDate: String,
    val strikes: Int,
    val isStrikes: Boolean,
    val logs: List<Log>,
    val monthlyCompletion: List<Date>,
    val totalCompletion: List<Date>,
    val monthlyRate: Int,
    val timestamp: String
)
