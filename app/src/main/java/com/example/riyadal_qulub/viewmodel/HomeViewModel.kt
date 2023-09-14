package com.example.riyadal_qulub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.riyadal_qulub.db.WirdDatabase
import com.example.riyadal_qulub.entity.Wird
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun addNewWird(database: WirdDatabase , wird: Wird) {
        viewModelScope.launch(Dispatchers.IO) {
            database.wirdDao().insertWird(wird)
            _wirds.postValue(database.wirdDao().getAll())
        }
    }
}