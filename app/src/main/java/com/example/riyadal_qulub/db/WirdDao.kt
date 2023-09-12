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

    @Query("SELECT * FROM wirds WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Wird>

    @Insert
    fun insertWird(vararg wird: Wird)

    @Delete
    fun delete(wird: Wird)
}