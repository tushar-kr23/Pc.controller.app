package com.tushar.pccontroller.ui

data class ControllerState(
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
    val select: Boolean = false
)