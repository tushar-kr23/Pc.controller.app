package com.tushar.pccontroller

class GyroProcessor {

    // ---------------------------------------------------------
    // Runtime Settings
    // ---------------------------------------------------------

    var sensitivityX: Float = 1.0f
        set(value) {
            field = value.coerceAtLeast(0f)
        }

    var sensitivityY: Float = 1.0f
        set(value) {
            field = value.coerceAtLeast(0f)
        }

    var invertX: Boolean = false

    var invertY: Boolean = false

    var deadzone: Float = 0.05f
        set(value) {
            field = value.coerceIn(0f, 0.99f)
        }

    var smoothing: Float = 0.2f
        set(value) {
            field = value.coerceIn(0f, 0.99f)
        }

    var maximumOutput: Float = 1.0f
        set(value) {
            field = value.coerceIn(0.01f, 1.0f)
        }

    // ---------------------------------------------------------
    // Calibration Offsets
    // ---------------------------------------------------------

    var calibrationX: Float = 0f
        private set

    var calibrationY: Float = 0f
        private set

    var calibrationZ: Float = 0f
        private set

    // ---------------------------------------------------------
    // Raw Sensor Values
    // ---------------------------------------------------------

    var rawX: Float = 0f
        private set

    var rawY: Float = 0f
        private set

    var rawZ: Float = 0f
        private set

    // ---------------------------------------------------------
    // Processed Values
    // ---------------------------------------------------------

    var processedX: Float = 0f
        private set

    var processedY: Float = 0f
        private set

    var processedZ: Float = 0f
        private set

    // ---------------------------------------------------------
    // Normalized Aim Values
    // ---------------------------------------------------------

    var aimX: Float = 0f
        private set

    var aimY: Float = 0f
        private set

    // ---------------------------------------------------------
    // Calibration
    // ---------------------------------------------------------

    fun calibrate(
        x: Float,
        y: Float,
        z: Float
    ) {
        calibrationX = x
        calibrationY = y
        calibrationZ = z

        resetProcessedValues()
    }

    fun clearCalibration() {
        calibrationX = 0f
        calibrationY = 0f
        calibrationZ = 0f

        resetProcessedValues()
    }

    // ---------------------------------------------------------
    // Process Raw Gyroscope Data
    // ---------------------------------------------------------

    fun process(
        x: Float,
        y: Float,
        z: Float
    ) {
        // Keep raw values separate.
        rawX = x
        rawY = y
        rawZ = z

        // Apply calibration offset.
        val calibratedX = x - calibrationX
        val calibratedY = y - calibrationY
        val calibratedZ = z - calibrationZ

        // Apply sensitivity.
        var outputX = calibratedX * sensitivityX
        var outputY = calibratedY * sensitivityY
        val outputZ = calibratedZ

        // Invert axes when requested.
        if (invertX) {
            outputX = -outputX
        }

        if (invertY) {
            outputY = -outputY
        }

        // Apply deadzone.
        outputX = applyDeadzone(outputX)
        outputY = applyDeadzone(outputY)

        // Smooth processed values.
        processedX = smooth(
            processedX,
            outputX
        )

        processedY = smooth(
            processedY,
            outputY
        )

        processedZ = smooth(
            processedZ,
            outputZ
        )

        // Convert to normalized aim values.
        aimX = normalize(processedX)
        aimY = normalize(processedY)
    }

    // ---------------------------------------------------------
    // Deadzone
    // ---------------------------------------------------------

    private fun applyDeadzone(value: Float): Float {

        val absoluteValue = kotlin.math.abs(value)

        if (absoluteValue <= deadzone) {
            return 0f
        }

        val range = 1f - deadzone

        val normalized =
            (absoluteValue - deadzone) / range

        return kotlin.math.sign(value) * normalized
    }

    // ---------------------------------------------------------
    // Smoothing
    // ---------------------------------------------------------

    private fun smooth(
        previous: Float,
        current: Float
    ): Float {

        val factor = 1f - smoothing

        return previous + (current - previous) * factor
    }

    // ---------------------------------------------------------
    // Maximum Output + Normalization
    // ---------------------------------------------------------

    private fun normalize(value: Float): Float {

        val limited = value.coerceIn(
            -maximumOutput,
            maximumOutput
        )

        return (
                limited / maximumOutput
                ).coerceIn(-1f, 1f)
    }

    // ---------------------------------------------------------
    // Reset
    // ---------------------------------------------------------

    fun resetProcessedValues() {
        rawX = 0f
        rawY = 0f
        rawZ = 0f

        processedX = 0f
        processedY = 0f
        processedZ = 0f

        aimX = 0f
        aimY = 0f
    }
}