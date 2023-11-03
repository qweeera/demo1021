package com.example.demo1021.time

import android.os.WorkSource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DialStyle(
    val stepsWidth: Dp = 1.2.dp,
    val stepsColor: Color = Color.Black,
    val normalStepsLineHeight: Dp = 4.dp,
    val fiveStepsLineHeight: Dp = 4.dp,
    val stepsTextStyle: TextStyle = TextStyle(),
    val stepsLabelTopPadding: Dp = 8.dp,
)


