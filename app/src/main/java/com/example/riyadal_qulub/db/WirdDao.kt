package com.example.riyadal_qulub.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.riyadal_qulub.entity.Wird

@Dao
interface WirdDao {
    @Query("SELECT * FROM wirds")
    fun getAll(): List<Wird>

    @Insert
    fun insertWird(vararg wird: Wird)

    @Delete
    fun delete(wird: Wird)

    @Query("UPDATE wirds SET doneDays = :doneDays WHERE id = :wirdId")
    fun updateDoneDays(wirdId: Int, doneDays: List<String>)

    //update isDone
    @Query("UPDATE wirds SET isDone = :isDone WHERE id = :wirdId")
    fun updateIsDone(wirdId: Int, isDone: Boolean)
}