import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demo1021.db.OfferDbItem
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import com.example.demo1021.MyViewModel
import com.example.demo1021.offer.DismissBackground


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OfferTaskList(
    myViewModel : MyViewModel,
    list: List<OfferDbItem>,
    paddingValues: PaddingValues,
    toDoTask: (OfferDbItem) -> Unit,
    toDeleteTask: (OfferDbItem) -> Unit,
    modifier: Modifier = Modifier,

    ) {
    LazyColumn(
        modifier = modifier.padding(vertical = 4.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = paddingValues,
    ) {
        items(
            items = list,
            key = {task -> task.id},
           
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
                            toDoTask(task)
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
                        DismissBackground(dismissState)
                    },
                    dismissThresholds  = { direction ->
                        FractionalThreshold(dismissThreshold) },

                    dismissContent = {
                      //  EmailMessageCard(emailMessage)
                        OfferTaskCard(
                            task = task,
                            myViewModel = myViewModel)
                    }
                )
            }
        }
    }
}

@Composable
private fun OfferTaskCard(
    task:OfferDbItem,
    myViewModel : MyViewModel) {

    var shouldShowDialog by rememberSaveable { mutableStateOf(false) }

    if (shouldShowDialog) {
   //     myViewModel.remove(task)
        modifyOfferDialog(myViewModel, task, {shouldShowDialog = false})
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(task.offerColor)
        ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable {
                shouldShowDialog = true
            }

    ) {
        OfferTaskItem(offerName = task.offerName)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun modifyOfferDialog(myViewModel : MyViewModel,
                              task:OfferDbItem,
                              onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            var offerName by remember { mutableStateOf(task.offerName) }
            var offerLabel by remember { mutableStateOf(task.offerLabel) }


            Column {
                TextField(
                    value = offerName,
                    onValueChange = { offerName = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = offerLabel,
                    onValueChange = { offerLabel = it },
                    label = { Text("Label") }
                )

                Row {
                    Button(
                        modifier = Modifier.padding(vertical = 24.dp),
                        onClick = {onDismissRequest()}
                    ) {
                        Text("dismiss")
                    }

                    Button(
                        modifier = Modifier.padding(vertical = 24.dp),
                        onClick = {
                            myViewModel.insert(OfferDbItem(0, offerName, offerLabel))
                            myViewModel.remove(task)
                            onDismissRequest()
                        }
                    ) {
                        Text("confirm")
                    }
                }
            }
        }
    }
}





