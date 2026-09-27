

package com.tushar.pccontroller

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class GyroManager(
    context: Context,
    private val onGyroUpdate: (x: Float, y: Float, z: Float) -> Unit
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val gyroscope: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private var isRunning = false

    /**
     * Returns true when the device has a gyroscope.
     */
    fun isAvailable(): Boolean {
        return gyroscope != null
    }

    /**
     * Start receiving gyroscope updates.
     */
    fun start() {
        if (isRunning) return

        val sensor = gyroscope ?: return

        sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_GAME
        )

        isRunning = true
    }

    /**
     * Stop receiving gyroscope updates.
     */
    fun stop() {
        if (!isRunning) return

        sensorManager.unregisterListener(this)
        isRunning = false
    }

    /**
     * Called whenever the gyroscope provides new data.
     *
     * x, y, z are angular velocity values.
     */
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        if (event.sensor.type != Sensor.TYPE_GYROSCOPE) {
            return
        }

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        onGyroUpdate(x, y, z)
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {
        // Accuracy handling will be added later if needed.
    }

    /**
     * Safety method for lifecycle cleanup.
     */
    fun release() {
        stop()
    }
}