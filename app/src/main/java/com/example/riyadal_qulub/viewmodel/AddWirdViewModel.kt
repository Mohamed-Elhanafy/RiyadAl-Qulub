package com.example.riyadal_qulub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.riyadal_qulub.db.WirdDatabase
import com.example.riyadal_qulub.entity.Wird
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWirdViewModel : ViewModel() {

    fun addNewWird(database: WirdDatabase , wird: Wird) {
        viewModelScope.launch(Dispatchers.IO) {
            database.wirdDao().insertWird(wird)
        }
    }

}