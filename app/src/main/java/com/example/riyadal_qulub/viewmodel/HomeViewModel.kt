package com.example.riyadal_qulub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.riyadal_qulub.db.WirdDatabase
import com.example.riyadal_qulub.entity.WeekDayItem
import com.example.riyadal_qulub.entity.Wird

import com.example.riyadal_qulub.utils.getCurrentDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeViewModel : ViewModel() {
    private val _wirds = MutableLiveData<List<Wird>>()
    val wirds: LiveData<List<Wird>> = _wirds


    fun getAllWirds(database: WirdDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            _wirds.postValue(database.wirdDao().getAll())
        }
    }

    fun updateDoneDays(database: WirdDatabase, wirdId: Int, doneDays: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            //update database and _wirds
            database.wirdDao().updateDoneDays(wirdId, doneDays)
            _wirds.postValue(database.wirdDao().getAll())
            _wirds.value!!.forEach {
                if (it.id == wirdId) {
                    it.doneDays = doneDays
                }
            }
        }
    }

    fun updateIsDone(database: WirdDatabase, wirdId: Int, isDone: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            //update database and _wirds
            database.wirdDao().updateIsDone(wirdId, isDone)
            _wirds.postValue(database.wirdDao().getAll())
            _wirds.value!!.forEach {
                if (it.id == wirdId) {
                    it.isDone = isDone
                }
            }
        }
    }

    fun addNewWird(database: WirdDatabase, wird: Wird) {
        viewModelScope.launch(Dispatchers.IO) {
            database.wirdDao().insertWird(wird)
            _wirds.postValue(database.wirdDao().getAll())
        }
    }

    // fun that take list of Dates and check if it's done return last 7 days as WeekDayItem
    fun getLastSevenDaysAsWeekDayItem(dates: List<Date>): List<WeekDayItem> {
        val calendar = Calendar.getInstance()
        val arabicLocale = Locale("ar", "SA") // Use Arabic locale
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", arabicLocale)
        val dateNumbers = mutableListOf<WeekDayItem>()

        for (i in 0 until 7) {
            if (dates.isNotEmpty()) {
                dates.forEach {
                    val dayOfWeek = dateFormat.format(it)
                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                    val weekDayItem = WeekDayItem(dayOfMonth, dayOfWeek, true)
                    dateNumbers.add(weekDayItem)
                    calendar.add(Calendar.DAY_OF_MONTH, -1)
                }
            } else {
                val dayOfWeek = dateFormat.format(calendar.time)
                val weekDayItem = WeekDayItem(i, dayOfWeek, false)
                dateNumbers.add(weekDayItem)
                calendar.add(Calendar.DAY_OF_MONTH, -1)
            }
        }

        return dateNumbers
    }

    fun addDayToDoneDays(database: WirdDatabase, wirdId: Int, doneDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val oldDoneDays = wirds.value!!.find { it.id == wirdId }!!.doneDays

            //check if doneDate is in oldDoneDays
            if (oldDoneDays.contains(doneDate)) {
                //remove doneDate from oldDoneDays
                val newDoneDays = mutableListOf<String>()
                oldDoneDays.forEach {
                    if (it != doneDate) {
                        newDoneDays.add(it)
                    }
                }
                //update database and _wirds
                database.wirdDao().updateDoneDays(wirdId, newDoneDays)
                _wirds.postValue(database.wirdDao().getAll())
                _wirds.value!!.forEach {
                    if (it.id == wirdId) {
                        it.doneDays = newDoneDays
                    }
                }
            } else {
                //add doneDate to oldDoneDays
                val newDoneDays = mutableListOf<String>()
                oldDoneDays.forEach {
                    newDoneDays.add(it)
                }
                newDoneDays.add(doneDate)
                //update database and _wirds
                database.wirdDao().updateDoneDays(wirdId, newDoneDays)
                _wirds.postValue(database.wirdDao().getAll())
                _wirds.value!!.forEach {
                    if (it.id == wirdId) {
                        it.doneDays = newDoneDays
                    }
                }
            }



        }
    }


}