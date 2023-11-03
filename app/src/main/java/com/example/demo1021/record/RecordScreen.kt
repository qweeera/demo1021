package com.example.demo1021.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.demo1021.MyViewModel
import com.example.demo1021.db.LabelItem
import com.example.demo1021.db.labelStruct
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun RecordScreen(
    modifier: Modifier = Modifier,
    myViewModel: MyViewModel,
    paddingValues: PaddingValues
) {
    val now = LocalDateTime.now()
    val today_start = now.withHour(0).withMinute(0).withSecond(0).withNano(0)
    val today_end = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999)
    val today_middle = now.withHour(12).withMinute(0).withSecond(0).withNano(0)
    val todayStartTime:Long = today_start.toEpochSecond(ZoneOffset.UTC) - 8 * 60 * 60  // 8小时时区
    val todayEndTime:Long = today_end.toEpochSecond(ZoneOffset.UTC) - 8 * 60 * 60
    val todayMiddleTime:Long = today_middle.toEpochSecond(ZoneOffset.UTC) - 8 * 60 * 60
    var currDay:Int by rememberSaveable { mutableStateOf(0) }

    val data by myViewModel.countByLabel.observeAsState(emptyList())
    myViewModel.queryTaskByLabel((todayStartTime + currDay * 86400) * 1000, (todayEndTime + currDay * 86400) * 1000)

    val first = mutableMapOf<String, labelStruct>()
    for (item in data) {
        var tmp:Long = item.timeSum / 1000 / 60
        first[item.taskLabel] = labelStruct(tmp.toInt(), item.taskColor)
    }

    Column(modifier = modifier.padding(vertical = 10.dp,horizontal = 12.dp)) {
        Row(modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Button(
                onClick = {
                    currDay -= 1
                    myViewModel.queryTaskByLabel((todayStartTime + currDay * 86400) * 1000, (todayEndTime + currDay * 86400) * 1000)
                }
            ) {
                Text("前一天")
            }

            Button(
                onClick = {
                    currDay += 1
                    myViewModel.queryTaskByLabel((todayStartTime + currDay * 86400) * 1000, (todayEndTime + currDay * 86400) * 1000)

                } // You'll learn about this callback later
            ) {
                Text("后一天")
            }

        }
        PieChartDemo(
            data = first, myViewModel
        )
    }
}