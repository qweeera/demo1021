package com.example.demo1021

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.demo1021.db.LabelDao
import com.example.demo1021.db.LabelItem
import com.example.demo1021.db.OfferDao
import com.example.demo1021.db.OfferDbItem
import com.example.demo1021.db.TimeDao
import com.example.demo1021.db.TimeDbItem
import com.example.demo1021.db.TimeLabel
import kotlinx.coroutines.launch


class MyViewModel(
                private val offerDao : OfferDao,
                private val timeDao : TimeDao,
                private val labelDao : LabelDao,
    ) : ViewModel() {

    val offers: LiveData<List<OfferDbItem>> = offerDao.getAllOffers()
    val tasks: LiveData<List<OfferDbItem>> = offerDao.getAllTasks()
    val labels: LiveData<List<LabelItem>> = labelDao.getAllLabels()

    var timeTag: MutableLiveData<List<TimeDbItem>> = MutableLiveData(emptyList())
    var taskByDate: MutableLiveData<List<TimeDbItem>> = MutableLiveData(emptyList())
    val task: MutableLiveData<OfferDbItem> = MutableLiveData(null)
    var countByLabel: MutableLiveData<List<TimeLabel>> = MutableLiveData(emptyList())
    val labelItem: MutableLiveData<Long> = MutableLiveData(0L)



    // 选中该任务  offer --> task
    fun select(item : OfferDbItem) {
        viewModelScope.launch {
            item.selected = true
            offerDao.update(item)
        }
    }

    fun remove(item : OfferDbItem) {
        viewModelScope.launch {
            offerDao.delete(item)
        }
    }

    fun insert(item : OfferDbItem) {
        viewModelScope.launch {
            offerDao.insert(item)
        }
    }

    fun insertColor(item : LabelItem) {
        viewModelScope.launch {
            labelDao.insert(item)
        }
    }

    fun addTimeTag(item : TimeDbItem) {
        viewModelScope.launch {
            timeDao.insert(item)
        }
    }

    fun queryTimeTag(taskId:Int) {
        viewModelScope.launch {
            timeTag.value = timeDao.queryTaskTime(taskId)
        }
    }

    fun queryTasksByDate(startTime:Long, endTime:Long) {
        viewModelScope.launch {
            taskByDate.value = timeDao.queryTaskByDate(startTime, endTime)
        }
    }

    fun queryTaskByLabel(startTime: Long, endTime: Long) {
        viewModelScope.launch {
            countByLabel.value = timeDao.queryLabelData(startTime, endTime)
        }
    }

    suspend fun queryTaskByLabel(label: String) : Long {
        return labelDao.getColorFromLabel(label)
    }






}

class MyViewModelFactory(
    private val offerDao : OfferDao,
    private val timeDao : TimeDao,
    private val labelDao : LabelDao,
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(offerDao, timeDao, labelDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}