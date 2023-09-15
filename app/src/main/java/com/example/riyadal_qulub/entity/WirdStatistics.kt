package com.example.riyadal_qulub.entity

data class WirdStatistics(
    val wirdId: Int,
    val wirdName: String,
    val wirdCount: Int,
    val wirdMonthlyCount: Int,
    val wirdMonthlyPercentage: Int,
    val wirdTotal: Int,
    val wirdStrikes: Int,
    val wirdDoneDates: List<String>
)