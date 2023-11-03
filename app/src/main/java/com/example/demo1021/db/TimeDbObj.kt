package com.example.demo1021.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TimeTable")
data class TimeDbItem (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var taskId: Int,
    var startTime: Long,
    var endTime: Long,
    var date: Long,
    var taskName: String,
    var taskLabel: String,
    var taskColor: Long,
)

data class TimeLabel (
    var taskLabel: String,
    var taskColor: Long,
    var timeSum:Long,
)

