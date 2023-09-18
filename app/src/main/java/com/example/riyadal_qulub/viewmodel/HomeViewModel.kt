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
                        it.isDone = true


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

    fun deleteWird(database: WirdDatabase, wird: Wird) {
        viewModelScope.launch(Dispatchers.IO) {
            database.wirdDao().delete(wird)
            _wirds.postValue(database.wirdDao().getAll())
        }

    }

    fun removeDayFromDoneDays(database: WirdDatabase, id: Int, currentDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val oldDoneDays = wirds.value!!.find { it.id == id }!!.doneDays
            val newDoneDays = mutableListOf<String>()
            oldDoneDays.forEach {
                if (it != currentDate) {
                    newDoneDays.add(it)
                }
            }
            //update database and _wirds
            database.wirdDao().updateIsDone(id, false)
            database.wirdDao().updateDoneDays(id, newDoneDays)
            _wirds.postValue(database.wirdDao().getAll())
            _wirds.value!!.forEach {
                if (it.id == id) {
                    it.doneDays = newDoneDays
                    it.isDone = false
                }
            }


        }
    }


}