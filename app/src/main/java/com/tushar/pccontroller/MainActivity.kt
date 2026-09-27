package com.tushar.pccontroller

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tushar.pccontroller.ui.ControllerScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Landscape controller
        requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Fullscreen / edge-to-edge
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                ControllerScreen()
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 1280,
    heightDp = 720
)
@Composable
fun MainActivityPreview() {
    MaterialTheme {
        ControllerScreen()
    }
}