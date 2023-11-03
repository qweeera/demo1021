package com.example.demo1021.task

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.demo1021.R
import com.example.demo1021.MyViewModel
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
@Composable
fun TaskItem(
    taskId: Int,
    offerName: String,
    myViewModel: MyViewModel,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                //      .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = offerName, style = MaterialTheme.typography.titleLarge)
            }
            IconButton(
                onClick = onPlay
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = stringResource(R.string.player)
                )
            }
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
        if (expanded) {
            myViewModel.queryTimeTag(taskId)
            val state by myViewModel.timeTag.observeAsState(emptyList())

            // SimpleDateFormat("YY-MM-DD-hh-mm-ss").format(time)
            for (item in state) {
                if (item.taskId == taskId)
                    Text(
                        "${SimpleDateFormat("YY/MM/dd HH:mm").format(item.startTime)}-" +
                                "${SimpleDateFormat("HH:mm").format(item.endTime)}"+
                                "  耗时：${formatTimeMills(item.endTime - item.startTime)}"
                    )

            }
        }
    }
}

fun formatTimeMills(time: Long): String {
    var value = time / 1000
    val seconds = value % 60
    value /= 60
    val minutes = value % 60
    value /= 60
    val hours = value % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}




