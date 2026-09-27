package com.tushar.pccontroller.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DPad(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 150.dp,
    buttonColor: Color = Color(0xFF25252B),
    pressedColor: Color = Color(0xFFE53935),
    textColor: Color = Color.White,
    onUp: () -> Unit = {},
    onDown: () -> Unit = {},
    onLeft: () -> Unit = {},
    onRight: () -> Unit = {}
) {
    val buttonSize = size / 3

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {

        // UP
        ControllerButton(
            text = "▲",
            buttonSize = buttonSize,
            buttonColor = buttonColor,
            textColor = textColor,
            modifier = Modifier.align(Alignment.TopCenter),
            onPressed = onUp,
            onReleased = {}
        )

        // DOWN
        ControllerButton(
            text = "▼",
            buttonSize = buttonSize,
            buttonColor = buttonColor,
            textColor = textColor,
            modifier = Modifier.align(Alignment.BottomCenter),
            onPressed = onDown,
            onReleased = {}
        )

        // LEFT
        ControllerButton(
            text = "◀",
            buttonSize = buttonSize,
            buttonColor = buttonColor,
            textColor = textColor,
            modifier = Modifier.align(Alignment.CenterStart),
            onPressed = onLeft,
            onReleased = {}
        )

        // RIGHT
        ControllerButton(
            text = "▶",
            buttonSize = buttonSize,
            buttonColor = buttonColor,
            textColor = textColor,
            modifier = Modifier.align(Alignment.CenterEnd),
            onPressed = onRight,
            onReleased = {}
        )

        // CENTER
        Box(
            modifier = Modifier
                .size(buttonSize)
                .background(
                    color = pressedColor.copy(alpha = 0.15f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "•",
                color = Color(0xFF66666F)
            )
        }
    }
}