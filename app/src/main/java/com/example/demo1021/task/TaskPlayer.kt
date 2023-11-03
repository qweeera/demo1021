package com.example.demo1021.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demo1021.MyViewModel


import kotlinx.coroutines.delay

@Composable
fun TimeControl(
    modifier: Modifier = Modifier,
    myViewModel: MyViewModel,
    onStart: () -> Unit,
    onStop: () -> Unit,
) {
    var time: Long by remember {
        mutableStateOf(0L)
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            time++
            delay(1000L)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 180.dp)
    ){
        Text(formatTime(time), modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Button(onClick = onStart){
                Text("Start")
            }
            Button(onClick = onStop) {
                Text("Stop")
            }
        }
    }
}

fun formatTime(time: Long): String {
    var value = time
    val seconds = value % 60
    value /= 60
    val minutes = value % 60
    value /= 60
    val hours = value % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
