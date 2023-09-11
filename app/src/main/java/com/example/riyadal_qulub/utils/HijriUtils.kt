package com.example.riyadal_qulub.utils

import java.util.Calendar

class HijriUtils {
    private val MILLIS_PER_DAY = (24 * 60 * 60 * 1000).toLong()
    private val MILLIS_PER_LONG_YEAR = 355L * MILLIS_PER_DAY
    private val MILLIS_PER_SHORT_YEAR = 354L * MILLIS_PER_DAY

    // millis of year of 0001-01-01
    private val MILLIS_YEAR_1 = -42521587200000L

    // 19 years are 354 days, 11 leap years of 355 days
    private val MILLIS_PER_CYCLE = (19L * 354L + 11L * 355L) * MILLIS_PER_DAY

    // length of the cycle
    private val CYCLE = 30

    // length of long month
    private val LONG_MONTH_LENGTH = 30

    // days in a pair of months
    private val MONTH_PAIR_LENGTH = 29 + 30

    // leap year pattern - based on 16-based  pattern
    // 2, 5, 7, 10, 13, 16, 18, 21, 24, 26, 29
    private val LEAP_YEAR_PATTERN = 623191204

    class HijriDate {
        var year = 0
        var month = 0
        var day = 0
    }

    /**
     * get the hijri date from a calendar
     * @param cal a calendar with the day to convert
     * @return the hijri date
     */
    fun getHijriDate(cal: Calendar): HijriDate? {
        return getHijriDate(
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DATE), cal.get(Calendar.YEAR)
        )
    }

    /**
     * given a day, month, and year, returns the hijri date
     * @param pMonth the current month (1 based)
     * @param pDay the current day
     * @param pYear the current year
     * @return the hijri date
     */
    fun getHijriDate(pMonth: Int, pDay: Int, pYear: Int): HijriDate? {
        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.YEAR, pYear)
        cal.set(Calendar.MONTH, pMonth - 1)
        cal.set(Calendar.DATE, pDay)
        val timestamp: Long = cal.getTimeInMillis()
        val hijriYear = getHijriYear(timestamp)
        val hijriMonth = getHijriMonth(timestamp, hijriYear)
        val hijriDate = getHijriDay(timestamp, hijriYear)
        val result = HijriDate()
        result.year = hijriYear
        result.month = hijriMonth
        result.day = hijriDate
        return result
    }

    private fun getHijriYear(timestamp: Long): Int {
        val millisIslamic = timestamp - MILLIS_YEAR_1
        val cycles = millisIslamic / MILLIS_PER_CYCLE
        var cycleRemainder = millisIslamic % MILLIS_PER_CYCLE
        var year = (cycles * CYCLE + 1L).toInt()
        var yearMillis = if (isHijriLeapYear(year)) MILLIS_PER_LONG_YEAR else MILLIS_PER_SHORT_YEAR
        while (cycleRemainder >= yearMillis) {
            cycleRemainder -= yearMillis
            yearMillis =
                if (isHijriLeapYear(++year)) MILLIS_PER_LONG_YEAR else MILLIS_PER_SHORT_YEAR
        }
        return year
    }

    private fun getHijriMonth(timestamp: Long, hYear: Int): Int {
        val doyZeroBased = ((timestamp - calculateFirstDayOfYearMillis(hYear)) /
                MILLIS_PER_DAY).toInt()
        return if (doyZeroBased == 354) {
            12
        } else doyZeroBased * 2 / MONTH_PAIR_LENGTH + 1
    }

    private fun getHijriDay(timestamp: Long, hYear: Int): Int {
        val yearStart = calculateFirstDayOfYearMillis(hYear)
        val dayOfYear = ((timestamp - yearStart) / MILLIS_PER_DAY).toInt()
        return if (dayOfYear == 354) {
            30
        } else dayOfYear % MONTH_PAIR_LENGTH % LONG_MONTH_LENGTH + 1
    }

    private fun calculateFirstDayOfYearMillis(year: Int): Long {
        var year = year
        year--
        val cycle = (year / CYCLE).toLong()
        var millis = MILLIS_YEAR_1 + cycle * MILLIS_PER_CYCLE
        val cycleRemainder = year % CYCLE + 1
        for (i in 1 until cycleRemainder) {
            millis += if (isHijriLeapYear(i)) MILLIS_PER_LONG_YEAR else MILLIS_PER_SHORT_YEAR
        }
        return millis
    }

    private fun isHijriLeapYear(year: Int): Boolean {
        val key = 1 shl year % 30
        return LEAP_YEAR_PATTERN and key > 0
    }
}