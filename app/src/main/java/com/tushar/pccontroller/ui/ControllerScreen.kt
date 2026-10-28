
package com.tushar.pccontroller.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tushar.pccontroller.ui.components.ControllerButton
import com.tushar.pccontroller.ui.components.DPad
import com.tushar.pccontroller.ui.components.Joystick

private const val TAG = "ControllerScreen"

@Composable
fun ControllerScreen(
    modifier: Modifier = Modifier
) {
    var leftStickX by remember { mutableFloatStateOf(0f) }
    var leftStickY by remember { mutableFloatStateOf(0f) }
    var rightStickX by remember { mutableFloatStateOf(0f) }
    var rightStickY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0F))
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LEFT SIDE
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ControllerButton(
                        text = "LT",
                        onPressed = { Log.d(TAG, "LT pressed") },
                        onReleased = { Log.d(TAG, "LT released") }
                    )
                    ControllerButton(
                        text = "LB",
                        onPressed = { Log.d(TAG, "LB pressed") },
                        onReleased = { Log.d(TAG, "LB released") }
                    )
                }

                DPad(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 15.dp),
                    onUp = { Log.d(TAG, "DPad UP") },
                    onDown = { Log.d(TAG, "DPad DOWN") },
                    onLeft = { Log.d(TAG, "DPad LEFT") },
                    onRight = { Log.d(TAG, "DPad RIGHT") }
                )

                Joystick(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 15.dp, bottom = 10.dp),
                    onValueChanged = { x, y ->
                        leftStickX = x
                        leftStickY = y
                        Log.d(TAG, "Left joystick: X=$x Y=$y")
                    },
                    onReleased = {
                        leftStickX = 0f
                        leftStickY = 0f
                        Log.d(TAG, "Left joystick released")
                    }
                )
            }

            // CENTER
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ControllerButton(
                        text = "VIEW",
                        buttonSize = 55.dp,
                        onPressed = { Log.d(TAG, "VIEW pressed") },
                        onReleased = { Log.d(TAG, "VIEW released") }
                    )
                    ControllerButton(
                        text = "MENU",
                        buttonSize = 55.dp,
                        onPressed = { Log.d(TAG, "MENU pressed") },
                        onReleased = { Log.d(TAG, "MENU released") }
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "L  X: %.2f  Y: %.2f".format(
                            leftStickX, leftStickY
                        ),
                        color = Color(0xFF777777)
                    )
                    Text(
                        text = "R  X: %.2f  Y: %.2f".format(
                            rightStickX, rightStickY
                        ),
                        color = Color(0xFF777777)
                    )
                }
            }

            // RIGHT SIDE
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ControllerButton(
                        text = "RT",
                        onPressed = { Log.d(TAG, "RT pressed") },
                        onReleased = { Log.d(TAG, "RT released") }
                    )
                    ControllerButton(
                        text = "RB",
                        onPressed = { Log.d(TAG, "RB pressed") },
                        onReleased = { Log.d(TAG, "RB released") }
                    )
                }

                // FACE BUTTONS: fixed-size diamond layout
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 105.dp, end = 35.dp)
                        .size(174.dp)
                ) {
                    ControllerButton(
                        modifier = Modifier.align(Alignment.TopCenter),
                        text = "Y",
                        onPressed = { Log.d(TAG, "Y pressed") },
                        onReleased = { Log.d(TAG, "Y released") }
                    )
                    ControllerButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = "X",
                        onPressed = { Log.d(TAG, "X pressed") },
                        onReleased = { Log.d(TAG, "X released") }
                    )
                    ControllerButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        text = "B",
                        onPressed = { Log.d(TAG, "B pressed") },
                        onReleased = { Log.d(TAG, "B released") }
                    )
                    ControllerButton(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        text = "A",
                        onPressed = { Log.d(TAG, "A pressed") },
                        onReleased = { Log.d(TAG, "A released") }
                    )
                }

                // RIGHT JOYSTICK: below face buttons
                Joystick(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 15.dp, bottom = 10.dp),
                    onValueChanged = { x, y ->
                        rightStickX = x
                        rightStickY = y
                        Log.d(TAG, "Right joystick: X=$x Y=$y")
                    },
                    onReleased = {
                        rightStickX = 0f
                        rightStickY = 0f
                        Log.d(TAG, "Right joystick released")
                    }
                )
            }
        }
    }
}
