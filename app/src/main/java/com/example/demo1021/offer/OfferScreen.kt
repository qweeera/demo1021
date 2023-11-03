import android.app.AlertDialog
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.CheckboxDefaults.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import com.example.demo1021.MyViewModel
import com.example.demo1021.db.LabelItem
import com.example.demo1021.db.OfferDbItem
import kotlin.math.roundToInt


@Composable
fun OfferScreen(
    modifier: Modifier = Modifier,
    myViewModel: MyViewModel,
    paddingValues: PaddingValues
) {

    var shouldShowDialog by rememberSaveable { mutableStateOf(false) }

    val state by myViewModel.offers.observeAsState(emptyList())
    OfferTaskList(
        myViewModel = myViewModel,
        list = state,
        paddingValues = paddingValues,
        toDoTask = {task -> myViewModel.select(task)},
        toDeleteTask = {task -> myViewModel.remove(task)}
    )

    if (shouldShowDialog) {
        AddOfferDialog(myViewModel, {shouldShowDialog = false})
    }

    var offsetX by remember { mutableStateOf(800f) }
    var offsetY by remember { mutableStateOf(1800f) }

    Box(
        Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(50.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    ) {
        FloatingActionButton(
                onClick = { shouldShowDialog = true }
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "add",
                )
            }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun AddOfferDialog(myViewModel : MyViewModel, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            var offerName by remember { mutableStateOf("") }
            var offerLabel by remember { mutableStateOf("") }
            var offerColor by remember { mutableStateOf(0L) }
            val labels by myViewModel.labels.observeAsState(emptyList())
            var shouldShowColorDialog by rememberSaveable { mutableStateOf(false) }


            if (shouldShowColorDialog) {
                ColorPickerDialog(myViewModel, onDismissRequest, { shouldShowColorDialog = false})
            }

            Column {
                TextField(
                    value = offerName,
                    onValueChange = { offerName = it },
                    label = { Text("Title") }
                )

                FlowRow {
                    for (item in labels) {
                        Button(
                            //   colors = colors(0xFFBB86FC),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(item.taskColor)),
                            onClick = {
                                offerColor = item.taskColor
                                offerLabel = item.taskLabel
                            }
                        ) {
                            Text("${item.taskLabel}")
                        }
                    }

                    Button(
                        onClick = {
                            shouldShowColorDialog = true
                        }
                    ) {
                        Text("Add")
                    }

                }


                Row {
                    Button(
                        modifier = Modifier.padding(vertical = 24.dp),
                        onClick = {}
                    ) {
                        Text("dismiss")
                    }

                    Button(
                        modifier = Modifier.padding(vertical = 24.dp),
                        onClick = {
                            myViewModel.insert(OfferDbItem(0, offerName, offerLabel, offerColor))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialog(myViewModel : MyViewModel, onDismissRequest: () -> Unit, onDone: ()->Unit)
{
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            var labelName by remember { mutableStateOf("") }
            var labelColor by remember { mutableStateOf(0xFFEF9A9A) }
            Column {


                ColorPicker(Color(0xFFCC3352), {labelColor = it})
                TextField(
                    value = labelName,
                    onValueChange = { labelName = it },
                    label = { Text("new label") }
                )
                Row {
                    Button(
                        modifier = Modifier.padding(vertical = 24.dp),
                        onClick = {}
                    ) {
                        Text("dismiss")
                    }

                    Button(
                        modifier = Modifier.padding(vertical = 24.dp),
                        onClick = {
                            myViewModel.insertColor(LabelItem(0, labelName, labelColor))
                            onDone()
                   //         onDismissRequest()
                        }
                    ) {
                        Text("confirm")
                    }
                }


            }

        }
    }

}





@Composable
fun ColorPicker(
    currentShowColor: Color,
    onColorConfirm: (Long) -> Unit
) {
    val colorList = listOf(
        0xFFEF9A9A,
        0xFFF48FB1,
        0xFFCE93D8,
        0xFFB39DDB,
        0xFF9FA8DA,

        0xFF90CAF9,
        0xFF81D4FA,

        0xFF80DEEA,
        0xFF80CBC4,
        0xFFA5D6A7,
        0xFFC5E1A5,


        0xFFE6EE9C,
        0xFFFFF59D,
        0xFFFFE082,
        0xFFFFCC80,

        0xFFFFAB91,
        0xFFBCAAA4,
        0xFFEEEEEE,



    )

    var currentColor by remember {
        mutableStateOf(currentShowColor)
    }

    LazyVerticalGrid(
        // 单元格展现形式
        columns = GridCells.Fixed(5),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {

            itemsIndexed(colorList) { index, item ->
                Surface(
                    modifier = Modifier
                        //宽高比
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .clickable {
                            currentColor = Color(item)
                            onColorConfirm(colorList[index])
                        },
                    shape = CircleShape,
                    color = Color(item),
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    if (currentColor == Color(item)) {
                        Icon(Icons.Default.Favorite, null, modifier = Modifier.padding(10.dp))
                    }

                }
            }

    }
}









