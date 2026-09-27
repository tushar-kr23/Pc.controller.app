package com.tushar.pccontroller.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

@Composable
fun Joystick(
    modifier: Modifier = Modifier,
    size: Dp = 130.dp,
    onValueChanged: (Float, Float) -> Unit = { _, _ -> },
    onReleased: () -> Unit = {}
) {
    var knobX by remember { mutableFloatStateOf(0f) }
    var knobY by remember { mutableFloatStateOf(0f) }

    val maxDistance = size.value * 0.30f

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(0xFF202027))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        knobX = 0f
                        knobY = 0f

                        onValueChanged(0f, 0f)
                        onReleased()
                    },
                    onDragCancel = {
                        knobX = 0f
                        knobY = 0f

                        onValueChanged(0f, 0f)
                        onReleased()
                    }
                ) { _, dragAmount ->

                    knobX += dragAmount.x
                    knobY += dragAmount.y

                    val distance = sqrt(
                        knobX * knobX + knobY * knobY
                    )

                    if (distance > maxDistance) {
                        val scale = maxDistance / distance

                        knobX *= scale
                        knobY *= scale
                    }

                    val normalizedX =
                        (knobX / maxDistance)
                            .coerceIn(-1f, 1f)

                    val normalizedY =
                        (knobY / maxDistance)
                            .coerceIn(-1f, 1f)

                    onValueChanged(
                        normalizedX,
                        normalizedY
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .offset(
                    x = knobX.dp,
                    y = knobY.dp
                )
                .size(size * 0.42f)
                .clip(CircleShape)
                .background(Color(0xFFE53935)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "●",
                color = Color.White
            )
        }
    }
}