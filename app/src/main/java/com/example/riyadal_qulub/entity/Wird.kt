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
    var wirdDays: List<String> = emptyList(),
    var doneDays: List<String> = emptyList(),
    var unit: String = "",
    var quantity: Int = 0,
    var isDaily: Boolean = false,
    var startDate: String = "",
)
