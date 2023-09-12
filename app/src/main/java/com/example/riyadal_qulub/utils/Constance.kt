package com.example.riyadal_qulub.utils

object PreferenceKeys {
    const val boardingSharedPref = "onBoarding"
    const val boardingFinished = "Finished"

    const val nameSharedPref = "name"
    const val nameStringSharedPref = "NAME"

}
object Prayers {
    const val FAJR = 0
    const val DUHA = 1
    const val DHUHR = 2
    const val ASR = 3
    const val MAGHRIB = 4
    const val ISHA = 5
    const val QIYYAM = 6
}
object WeekDays{
    const val SATURDAY = 0
    const val SUNDAY = 1
    const val MONDAY = 2
    const val TUESDAY = 3
    const val WEDNESDAY = 4
    const val THURSDAY = 5
    const val FRIDAY = 6
}

var PRAYER_LIST = intArrayOf(
    Prayers.FAJR, Prayers.DHUHR, Prayers.ASR,
    Prayers.MAGHRIB, Prayers.ISHA
)

object PrayerType {
    const val GROUP_WITH_VOLUNTARY = 4
    const val GROUP = 3
    const val ALONE_WITH_VOLUNTARY = 2
    const val ALONE = 1
    const val LATE = 5
    const val NOT_SET = 0
    const val EXCUSED = 6
}

object CharityTypes {
    const val MONEY = 0
    const val EFFORT = 1
    const val FOOD = 2
    const val CLOTHES = 3
    const val SMILE = 4
    const val OTHER = 5
}

object FastingTypes {
    const val FAREEDAH = 1
    const val SUNNAH = 2
    const val QADA2 = 3
    const val KAFFARA = 4
    const val NAZR = 5
    const val NONE = 0
}

object DatabaseConstants {
     const val DATABASE_NAME = "wird_database"
}