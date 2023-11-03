package com.example.demo1021.time


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.LiveData
import com.example.demo1021.MyViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun TimeScreen(
    modifier: Modifier = Modifier,
    myViewModel: MyViewModel,
    paddingValues: PaddingValues
) {

    val now = LocalDateTime.now()
    val today_start = now.withHour(0).withMinute(0).withSecond(0).withNano(0)
    val today_end = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999)
    val today_middle = now.withHour(12).withMinute(0).withSecond(0).withNano(0)
    val todayStartTime:Long = today_start.toEpochSecond(ZoneOffset.UTC) - 8 * 60 * 60
    val todayEndTime:Long = today_end.toEpochSecond(ZoneOffset.UTC) - 8 * 60 * 60
    val todayMiddleTime:Long = today_middle.toEpochSecond(ZoneOffset.UTC) - 8 * 60 * 60
    var currentDay:Int by rememberSaveable { mutableStateOf(0) }

    myViewModel.queryTasksByDate((todayStartTime + currentDay * 86400) * 1000, (todayEndTime + currentDay * 86400) * 1000)

    Column(modifier = modifier.padding(vertical = 10.dp,horizontal = 12.dp)) {
        Row(modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Button(
                onClick = {
                    currentDay -= 1
                    myViewModel.queryTasksByDate((todayStartTime + currentDay * 86400) * 1000, (todayEndTime + currentDay * 86400) * 1000)
                } // You'll learn about this callback later
            ) {
                Text("前一天")
            }

            Button(
                onClick = {
                    currentDay += 1
                    myViewModel.queryTasksByDate((todayStartTime + currentDay * 86400) * 1000, (todayEndTime + currentDay * 86400) * 1000)
                } // You'll learn about this callback later
            ) {
                Text("后一天")
            }

        }
        Clock(myViewModel = myViewModel, todayStartTime = todayStartTime + currentDay * 86400, todayMiddleTime = todayMiddleTime + currentDay * 86400)
        ShowTask(modifier, myViewModel, paddingValues)
    }
}


@Composable
fun ShowTask(modifier: Modifier = Modifier, myViewModel: MyViewModel, paddingValues: PaddingValues) {
    val state by myViewModel.taskByDate.observeAsState(emptyList())


    LazyColumn(
        modifier = modifier.padding(vertical = 4.dp),
        contentPadding = paddingValues,
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = state,
            key = { task -> task.id }
        ) { task ->
            Row{
                Text("${SimpleDateFormat("YY/MM/dd HH:mm").format(task.startTime)} : " +
                        "${SimpleDateFormat("HH:mm").format(task.endTime)}  ${task.taskName}")

            }

        }
    }
}


















