package com.example.riyadal_qulub.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.riyadal_qulub.utils.WeekDays
import java.util.Date

@Entity(tableName = "wirds")
data class Wird(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var count: Int = 0,
    var countDone: Int = 0,
    var isDone: Boolean = false,
    var isAlarm: Boolean = false,
    var alarmTime: String = "",
    var wirdDays: List<Int> = emptyList(),
    var unit: String = "",
    var quantity: Int = 0,
    var isDaily: Boolean = false,
    var customDays: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var strikes: Int = 0,
    var isStrikes: Boolean = false,
    var logs: List<Log> = emptyList(),
    var monthlyCompletion: List<Date> = emptyList(),
    var totalCompletion: List<Date> = emptyList(),
    var monthlyRate: Int = 0,
    var timestamp: String = ""

)
