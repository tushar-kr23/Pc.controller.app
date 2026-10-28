
package com.tushar.pccontroller.controller

data class ControllerState(
    val a: Boolean = false,
    val b: Boolean = false,
    val x: Boolean = false,
    val y: Boolean = false,
    val lb: Boolean = false,
    val rb: Boolean = false,
    val lt: Boolean = false,
    val rt: Boolean = false,
    val view: Boolean = false,
    val menu: Boolean = false,
    val dpadUp: Boolean = false,
    val dpadDown: Boolean = false,
    val dpadLeft: Boolean = false,
    val dpadRight: Boolean = false,
    val leftStickX: Float = 0f,
    val leftStickY: Float = 0f,
    val rightStickX: Float = 0f,
    val rightStickY: Float = 0f
)
