package com.example.demo1021.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offerTable")
data class OfferDbItem (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var offerName: String,
    var offerLabel: String = "",
    var offerColor: Long = 0L,
    var selected: Boolean = false,
)