package com.example.demo1021.db

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LabelTable")
data class LabelItem (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var taskLabel: String,
    var taskColor: Long,
)

data class labelStruct(
    var num: Int,
    var color: Long,
)

