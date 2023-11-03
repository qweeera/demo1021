package com.example.demo1021.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface OfferDao {
    @Insert
    suspend fun insert(item: OfferDbItem)

    @Update
    suspend fun update(item: OfferDbItem)

    @Delete
    suspend fun delete(item: OfferDbItem)

    @Query("SELECT * from offerTable WHERE selected=0")
    fun getAllOffers(): LiveData<List<OfferDbItem>>

    @Query("SELECT * from offerTable WHERE selected=1")
    fun getAllTasks(): LiveData<List<OfferDbItem>>



}