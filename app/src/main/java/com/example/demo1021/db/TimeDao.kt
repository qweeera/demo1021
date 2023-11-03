package com.example.demo1021.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TimeDao {
    @Insert
    suspend fun insert(item: TimeDbItem)

    @Update
    suspend fun update(item: TimeDbItem)

    @Delete
    suspend fun delete(item: TimeDbItem)

    @Query("SELECT * from TimeTable WHERE taskId = :task order by startTime ")
    suspend fun queryTaskTime(task:Int): List<TimeDbItem>

    @Query("SELECT * from TimeTable WHERE startTime > :startTime and startTime < :endTime order by startTime ")
    suspend fun queryTaskByDate(startTime:Long, endTime:Long): List<TimeDbItem>

    @Query("SELECT taskLabel, taskColor, sum(endTime - startTime) timeSum from TimeTable WHERE startTime > :startTime and startTime < :endTime group by taskLabel")
    suspend fun queryLabelData(startTime:Long, endTime:Long): List<TimeLabel>
}