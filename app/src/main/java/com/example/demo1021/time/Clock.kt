package com.example.demo1021.time

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.example.demo1021.MyViewModel
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

private const val TAG = "Clock"

@OptIn(ExperimentalTextApi::class)
@Composable
fun Clock(
    modifier: Modifier =  Modifier.size(340.dp),
    myViewModel: MyViewModel,
    todayStartTime:Long,
    todayMiddleTime:Long,

) {
    val state by myViewModel.taskByDate.observeAsState(emptyList())

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
    ) {
        val label_big = minOf(this.size.width, this.size.height) / 2f - 100f
        val label_len = 4.dp.toPx()
        val h0 = 80f

        // 最外面的大圆
        dial1(
            radius = label_big,
            rotation = 90f,
            textMeasurer = textMeasurer,
        )

        val circle_big_r = label_big - label_len - h0/2
        val circle_big_topleft = Offset(x = this.center.x - circle_big_r, y = this.center.y - circle_big_r)
        drawArc(
            color = Color.LightGray,
            startAngle = -90F,
            sweepAngle = 360F,
            useCenter = true,
            topLeft = circle_big_topleft,
            style = Stroke(
                width = h0,   // 增加圆心厚度
                cap = StrokeCap.Butt
            ),
            size = Size(circle_big_r*2,circle_big_r*2),  // 调整圆圈大小
        )

        var circle_little_r = label_big - label_len - h0/2 -h0
        var circle_little_topleft = Offset(x = this.center.x - circle_little_r, y = this.center.y - circle_little_r)
        drawArc(
            color = Color.LightGray,
            startAngle = -90F,
            sweepAngle = 360F,
            useCenter = true,
            topLeft = circle_little_topleft,
            style = Stroke(
                width = h0,   // 增加圆心厚度
                cap = StrokeCap.Butt
            ),
            size = Size(circle_little_r*2,circle_little_r*2),  // 调整圆圈大小
        )
        val label_little = circle_little_r - h0/2
        dial2(
            radius = label_little,
            rotation = 90f,
            textMeasurer = textMeasurer,
        )

        for (item in state) {
            // 先画小圈
            if (item.startTime / 1000F <= todayMiddleTime) {
                var startAngle: Float = 0F
                var area: Float = 0F

                if (item.endTime / 1000F <= todayMiddleTime) { // 都在小圈里面
                    startAngle = (item.startTime / 1000F - todayStartTime) / 60F
                    area = (item.endTime - item.startTime) / 1000F / 60F
                } else {
                    startAngle = (item.startTime / 1000F - todayStartTime) / 60F
                    area = (todayMiddleTime - item.startTime / 1000F) / 60F
                }
                drawArc(
                    color = Color(item.taskColor),
                    startAngle = -90F + startAngle / 2F,
                    sweepAngle = area / 2F,
                    useCenter = false,
                    topLeft = circle_little_topleft,
                    style = Stroke(
                        width = h0,   // 增加圆心厚度
                        cap = StrokeCap.Butt
                    ),
                    size = Size(circle_little_r * 2, circle_little_r * 2),  // 调整圆圈大小
                )
            }
            if (item.endTime / 1000F >= todayMiddleTime) {
                var startAngle: Float = 0F
                var area: Float = 0F

                // 全在大圈
                if (item.startTime / 1000F >= todayMiddleTime) {
                    startAngle = (item.startTime / 1000F - todayMiddleTime) / 60F
                    area = (item.endTime - item.startTime) / 1000F / 60F
                } else { // 从中午开始画大圈
                    startAngle = (todayMiddleTime - todayStartTime) / 60F
                    area = (item.endTime / 1000F - todayMiddleTime) / 60F

                }
                drawArc(
                    color = Color(item.taskColor),
                    startAngle = -90F + startAngle / 2F,
                    sweepAngle = area / 2F,
                    useCenter = false,
                    topLeft = circle_big_topleft,
                    style = Stroke(
                        width = h0,   // 增加圆心厚度
                        cap = StrokeCap.Butt
                    ),
                    size = Size(circle_big_r*2,circle_big_r*2),  // 调整圆圈大小
                )

            }
        }
    }
}





