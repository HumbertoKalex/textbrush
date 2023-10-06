package com.example.textbrush

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.text.TextStyle
import kotlin.math.atan2

@Composable
fun TextBrushCanvas(
    modifier: Modifier = Modifier,
    brushText: String = "HELLO",
    style: TextStyle = TextStyle.Default
) {
    var textSize by remember { mutableStateOf(style.fontSize.value) }
    var offsets by remember { mutableStateOf(listOf<OffsetWithAngle>()) }
    var lastPoint by remember { mutableStateOf(Offset(0f, 0f)) }

    Canvas(
        modifier = modifier.pointerInput(Unit) {
            detectTransformGestures { _, _, zoom, _ ->
                textSize *= zoom
            }

            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()

                    if (event.changes.first().pressed) {
                        lastPoint = event.changes.first().position
                    } else {
                        val currentPosition = event.changes.first().position
                        val angle = atan2(
                            currentPosition.y - lastPoint.y,
                            currentPosition.x - lastPoint.x
                        )

                        offsets = offsets + OffsetWithAngle(currentPosition, angle)
                        lastPoint = currentPosition
                    }
                }
            }
        }
    ) {
        offsets.forEach { offsetWithAngle ->
            drawTextOnCanvas( brushText, offsetWithAngle, textSize, style.color)
        }
    }
}


data class OffsetWithAngle(val offset: Offset, val angle: Float)

fun DrawScope.drawTextOnCanvas(
    text: String,
    offsetWithAngle: OffsetWithAngle,
    textSize: Float,
    color: Color
) {
    val paint = android.graphics.Paint().apply {
        this.textSize = textSize
        this.color = color.toArgb()
    }

    val charWidth = paint.measureText(text, 0, 1)
    val dx = charWidth * kotlin.math.cos(offsetWithAngle.angle)
    val dy = charWidth * kotlin.math.sin(offsetWithAngle.angle)

    this.drawContext.canvas.nativeCanvas.save()
    this.drawContext.canvas.nativeCanvas.rotate(
        offsetWithAngle.angle * (180f / kotlin.math.PI).toFloat(),
        offsetWithAngle.offset.x,
        offsetWithAngle.offset.y
    )

    text.forEachIndexed { index, char ->
        this.drawContext.canvas.nativeCanvas.drawText(
            char.toString(),
            offsetWithAngle.offset.x + dx * index,
            offsetWithAngle.offset.y + dy * index,
            paint
        )
    }

    this.drawContext.canvas.nativeCanvas.restore()
}