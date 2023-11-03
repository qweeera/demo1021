package com.example.demo1021.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.demo1021.MyViewModel
import com.example.demo1021.db.OfferDbItem



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskList(
    list: List<OfferDbItem>,
    myViewModel: MyViewModel,
    paddingValues: PaddingValues,
    toDeleteTask: (OfferDbItem) -> Unit,
    onDoneTask: (OfferDbItem) -> Unit,
    onPlayTask: (OfferDbItem) -> Unit,
    modifier: Modifier = Modifier,

    ) {
    LazyColumn(
        modifier = modifier.padding(vertical = 4.dp),
        contentPadding = paddingValues,
    ) {
        items(
            items = list,
            key = {task -> task.id}
        ) { task ->
            var show by remember { mutableStateOf(true) }
            val dismissThreshold = 0.6f
            val currentFraction = remember { mutableStateOf(0f) }

            val dismissState = rememberDismissState(
                confirmStateChange = {  // modify in here
                    if (it == DismissValue.DismissedToStart &&
                        currentFraction.value >= dismissThreshold &&
                        currentFraction.value < 1.0f) {
                        toDeleteTask(task)
                        show = false
                        true

                    } else if (it == DismissValue.DismissedToEnd &&
                        currentFraction.value >= dismissThreshold &&
                        currentFraction.value < 1.0f){
                        onDoneTask(task)
                        show = false
                        true
                    } else {
                        false
                    }
                }
            )
            AnimatedVisibility(
                show,exit = fadeOut(spring())
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier,
                    background = {
                        currentFraction.value = dismissState.progress.fraction
                        TaskDismissBackground(dismissState)
                    },
                    dismissThresholds  = { direction ->
                        FractionalThreshold(dismissThreshold) },
                    dismissContent = {
                        OfferTaskCard(
                                    task,
                                    myViewModel,
                                    onPlay = {onPlayTask(task)})
                    }
                )
            }
        }
    }
}

@Composable
private fun OfferTaskCard(
    task:OfferDbItem,
    myViewModel: MyViewModel,
    onPlay: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(task.offerColor)
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {

        TaskItem(task.id, offerName = task.offerName, myViewModel, onPlay = onPlay)
    }
}



