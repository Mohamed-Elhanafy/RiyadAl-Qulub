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


    fun getAllWirds(database: WirdDatabase){
        viewModelScope.launch(Dispatchers.IO) {
            _wirds.postValue(database.wirdDao().getAll())
        }
    }

}