
package com.tushar.pccontroller

import java.nio.ByteBuffer
import java.nio.ByteOrder

data class ControllerPacket(
    val leftStickX: Float = 0f,
    val leftStickY: Float = 0f,
    val rightStickX: Float = 0f,
    val rightStickY: Float = 0f,

    val up: Boolean = false,
    val down: Boolean = false,
    val left: Boolean = false,
    val right: Boolean = false,

    val a: Boolean = false,
    val b: Boolean = false,
    val x: Boolean = false,
    val y: Boolean = false,

    val lb: Boolean = false,
    val rb: Boolean = false,

    val lt: Float = 0f,
    val rt: Float = 0f,

    val start: Boolean = false,
    val select: Boolean = false,

    val gyroX: Float = 0f,
    val gyroY: Float = 0f,

    val timestamp: Long = System.currentTimeMillis()
) {
    fun toByteArray(): ByteArray {
        var mask = 0

        val buttons = listOf(
            up, down, left, right,
            a, b, x, y,
            lb, rb, start, select
        )

        buttons.forEachIndexed { index, pressed ->
            if (pressed) mask = mask or (1 shl index)
        }

        return ByteBuffer.allocate(PACKET_SIZE)
            .order(ByteOrder.LITTLE_ENDIAN)
            .putLong(timestamp)
            .putFloat(leftStickX.coerceIn(-1f, 1f))
            .putFloat(leftStickY.coerceIn(-1f, 1f))
            .putFloat(rightStickX.coerceIn(-1f, 1f))
            .putFloat(rightStickY.coerceIn(-1f, 1f))
            .putInt(mask)
            .putFloat(lt.coerceIn(0f, 1f))
            .putFloat(rt.coerceIn(0f, 1f))
            .putFloat(gyroX)
            .putFloat(gyroY)
            .array()
    }

    companion object {
        const val PACKET_SIZE = 44

        fun fromByteArray(data: ByteArray): ControllerPacket? {
            if (data.size < PACKET_SIZE) return null

            return try {
                val buffer = ByteBuffer.wrap(data)
                    .order(ByteOrder.LITTLE_ENDIAN)

                val timestamp = buffer.long
                val lx = buffer.float
                val ly = buffer.float
                val rx = buffer.float
                val ry = buffer.float
                val mask = buffer.int
                val lt = buffer.float.coerceIn(0f, 1f)
                val rt = buffer.float.coerceIn(0f, 1f)
                val gx = buffer.float
                val gy = buffer.float

                fun pressed(bit: Int) =
                    (mask and (1 shl bit)) != 0

                ControllerPacket(
                    leftStickX = lx,
                    leftStickY = ly,
                    rightStickX = rx,
                    rightStickY = ry,
                    up = pressed(0),
                    down = pressed(1),
                    left = pressed(2),
                    right = pressed(3),
                    a = pressed(4),
                    b = pressed(5),
                    x = pressed(6),
                    y = pressed(7),
                    lb = pressed(8),
                    rb = pressed(9),
                    start = pressed(10),
                    select = pressed(11),
                    lt = lt,
                    rt = rt,
                    gyroX = gx,
                    gyroY = gy,
                    timestamp = timestamp
                )
            } catch (_: Exception) {
                null
            }
        }
    }
}
