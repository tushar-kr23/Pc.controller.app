package com.tushar.pccontroller.ui

class InputMapper {

    private var state = ControllerState()

    fun getState(): ControllerState {
        return state
    }

    // ---------------------------------------------------------
    // Face Buttons
    // ---------------------------------------------------------

    fun setA(pressed: Boolean) {
        state = state.copy(a = pressed)
    }

    fun setB(pressed: Boolean) {
        state = state.copy(b = pressed)
    }

    fun setX(pressed: Boolean) {
        state = state.copy(x = pressed)
    }

    fun setY(pressed: Boolean) {
        state = state.copy(y = pressed)
    }

    // ---------------------------------------------------------
    // Shoulder Buttons
    // ---------------------------------------------------------

    fun setLB(pressed: Boolean) {
        state = state.copy(lb = pressed)
    }

    fun setRB(pressed: Boolean) {
        state = state.copy(rb = pressed)
    }

    // ---------------------------------------------------------
    // Triggers
    // ---------------------------------------------------------

    fun setLT(value: Float) {
        state = state.copy(
            lt = value.coerceIn(0f, 1f)
        )
    }

    fun setRT(value: Float) {
        state = state.copy(
            rt = value.coerceIn(0f, 1f)
        )
    }

    // ---------------------------------------------------------
    // Start / Select
    // ---------------------------------------------------------

    fun setStart(pressed: Boolean) {
        state = state.copy(start = pressed)
    }

    fun setSelect(pressed: Boolean) {
        state = state.copy(select = pressed)
    }

    // ---------------------------------------------------------
    // D-Pad
    // ---------------------------------------------------------

    fun setUp(pressed: Boolean) {
        state = state.copy(up = pressed)
    }

    fun setDown(pressed: Boolean) {
        state = state.copy(down = pressed)
    }

    fun setLeft(pressed: Boolean) {
        state = state.copy(left = pressed)
    }

    fun setRight(pressed: Boolean) {
        state = state.copy(right = pressed)
    }

    // ---------------------------------------------------------
    // Left Joystick
    // ---------------------------------------------------------

    fun setLeftStick(x: Float, y: Float) {
        state = state.copy(
            leftStickX = x.coerceIn(-1f, 1f),
            leftStickY = y.coerceIn(-1f, 1f)
        )
    }

    fun resetLeftStick() {
        state = state.copy(
            leftStickX = 0f,
            leftStickY = 0f
        )
    }

    // ---------------------------------------------------------
    // Right Joystick
    // ---------------------------------------------------------

    fun setRightStick(x: Float, y: Float) {
        state = state.copy(
            rightStickX = x.coerceIn(-1f, 1f),
            rightStickY = y.coerceIn(-1f, 1f)
        )
    }

    fun resetRightStick() {
        state = state.copy(
            rightStickX = 0f,
            rightStickY = 0f
        )
    }

    // ---------------------------------------------------------
    // Reset Everything
    // ---------------------------------------------------------

    fun reset() {
        state = ControllerState()
    }
}