package com.example.demo1021.task

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.demo1021.MyViewModel
import com.example.demo1021.db.TimeDbItem


@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    myViewModel: MyViewModel,
    paddingValues: PaddingValues
) {
    var IsTaskPlay: Boolean by remember { mutableStateOf(false) }
    var taskId:Int by remember { mutableStateOf(0) }
    var startTime:Long by remember { mutableStateOf(0L) }
    var taskName:String by remember { mutableStateOf("") }
    var taskLabel:String by remember { mutableStateOf("") }
    var taskColor:Long by remember { mutableStateOf(0L) }

    if (IsTaskPlay) {
        TimeControl(
            modifier,
            myViewModel,
            onStart = {},
            onStop = {   // 任务停止
                IsTaskPlay = false
                var endTime:Long = System.currentTimeMillis()
                val tmp:TimeDbItem = TimeDbItem(0,taskId,startTime,endTime,0, taskName, taskLabel, taskColor)
                myViewModel.addTimeTag(tmp)
            }
        )
    } else {
        val state by myViewModel.tasks.observeAsState(emptyList())
        TaskList(list = state,
            myViewModel,
            paddingValues,
            toDeleteTask = {task -> myViewModel.remove(task)},
            onDoneTask = {task -> myViewModel.remove(task)},
            onPlayTask = {
                IsTaskPlay = true
                taskId = it.id
                startTime = System.currentTimeMillis()
                taskName = it.offerName
                taskLabel = it.offerLabel
                taskColor = it.offerColor

        })
    }

}