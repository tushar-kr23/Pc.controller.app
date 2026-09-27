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

    companion object {

        const val PACKET_SIZE = 54

        private const val UP = 0
        private const val DOWN = 1
        private const val LEFT = 2
        private const val RIGHT = 3

        private const val A = 4
        private const val B = 5
        private const val X = 6
        private const val Y = 7

        private const val LB = 8
        private const val RB = 9

        private const val START = 10
        private const val SELECT = 11

        private fun setBit(
            mask: Int,
            bit: Int,
            value: Boolean
        ): Int {
            return if (value) {
                mask or (1 shl bit)
            } else {
                mask
            }
        }

        private fun hasBit(
            mask: Int,
            bit: Int
        ): Boolean {
            return (mask and (1 shl bit)) != 0
        }

        fun fromByteArray(
            data: ByteArray
        ): ControllerPacket? {

            if (data.size < PACKET_SIZE) {
                return null
            }

            return try {

                val buffer = ByteBuffer
                    .wrap(data)
                    .order(ByteOrder.LITTLE_ENDIAN)

                val timestamp = buffer.long

                val leftStickX = buffer.float
                val leftStickY = buffer.float

                val rightStickX = buffer.float
                val rightStickY = buffer.float

                val buttonMask =
                    buffer.int

                val lt = buffer.float
                val rt = buffer.float

                val gyroX = buffer.float
                val gyroY = buffer.float

                ControllerPacket(
                    leftStickX = leftStickX,
                    leftStickY = leftStickY,

                    rightStickX = rightStickX,
                    rightStickY = rightStickY,

                    up = hasBit(buttonMask, UP),
                    down = hasBit(buttonMask, DOWN),
                    left = hasBit(buttonMask, LEFT),
                    right = hasBit(buttonMask, RIGHT),

                    a = hasBit(buttonMask, A),
                    b = hasBit(buttonMask, B),
                    x = hasBit(buttonMask, X),
                    y = hasBit(buttonMask, Y),

                    lb = hasBit(buttonMask, LB),
                    rb = hasBit(buttonMask, RB),

                    lt = lt.coerceIn(0f, 1f),
                    rt = rt.coerceIn(0f, 1f),

                    start = hasBit(buttonMask, START),
                    select = hasBit(buttonMask, SELECT),

                    gyroX = gyroX,
                    gyroY = gyroY,

                    timestamp = timestamp
                )

            } catch (_: Exception) {
                null
            }
        }
    }

    fun toByteArray(): ByteArray {

        var buttonMask = 0

        buttonMask = setBit(buttonMask, UP, up)
        buttonMask = setBit(buttonMask, DOWN, down)
        buttonMask = setBit(buttonMask, LEFT, left)
        buttonMask = setBit(buttonMask, RIGHT, right)

        buttonMask = setBit(buttonMask, A, a)
        buttonMask = setBit(buttonMask, B, b)
        buttonMask = setBit(buttonMask, X, x)
        buttonMask = setBit(buttonMask, Y, y)

        buttonMask = setBit(buttonMask, LB, lb)
        buttonMask = setBit(buttonMask, RB, rb)

        buttonMask = setBit(buttonMask, START, start)
        buttonMask = setBit(buttonMask, SELECT, select)

        val buffer = ByteBuffer
            .allocate(PACKET_SIZE)
            .order(ByteOrder.LITTLE_ENDIAN)

        buffer.putLong(timestamp)

        buffer.putFloat(leftStickX)
        buffer.putFloat(leftStickY)

        buffer.putFloat(rightStickX)
        buffer.putFloat(rightStickY)

        buffer.putInt(buttonMask)

        buffer.putFloat(lt)
        buffer.putFloat(rt)

        buffer.putFloat(gyroX)
        buffer.putFloat(gyroY)

        return buffer.array()
    }
}