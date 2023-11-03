package com.example.demo1021.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LabelDao {
    @Insert
    suspend fun insert(item: LabelItem)

    @Update
    suspend fun update(item: LabelItem)

    @Delete
    suspend fun delete(item: LabelItem)

    @Query("SELECT * from LabelTable")
    fun getAllLabels(): LiveData<List<LabelItem>>

    @Query("SELECT taskColor from LabelTable WHERE taskLabel=:label")
    suspend fun getColorFromLabel(label: String): Long
}