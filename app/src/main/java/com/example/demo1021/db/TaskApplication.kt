package com.example.demo1021.db

import android.app.Application

class TaskApplication: Application() {
    val database by lazy {InventoryDatabase.getDatabase(this)}
}