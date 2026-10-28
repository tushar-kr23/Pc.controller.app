
package com.tushar.pccontroller.controller

object InputMapper {

    fun button(
        state: ControllerState,
        name: String,
        pressed: Boolean
    ): ControllerState {
        return when (name.uppercase()) {
            "A" -> state.copy(a = pressed)
            "B" -> state.copy(b = pressed)
            "X" -> state.copy(x = pressed)
            "Y" -> state.copy(y = pressed)
            "LB" -> state.copy(lb = pressed)
            "RB" -> state.copy(rb = pressed)
            "LT" -> state.copy(lt = pressed)
            "RT" -> state.copy(rt = pressed)
            "VIEW" -> state.copy(view = pressed)
            "MENU" -> state.copy(menu = pressed)
            "DPAD_UP" -> state.copy(dpadUp = pressed)
            "DPAD_DOWN" -> state.copy(dpadDown = pressed)
            "DPAD_LEFT" -> state.copy(dpadLeft = pressed)
            "DPAD_RIGHT" -> state.copy(dpadRight = pressed)
            else -> state
        }
    }

    fun leftStick(
        state: ControllerState,
        x: Float,
        y: Float
    ): ControllerState {
        return state.copy(
            leftStickX = x.coerceIn(-1f, 1f),
            leftStickY = y.coerceIn(-1f, 1f)
        )
    }

    fun rightStick(
        state: ControllerState,
        x: Float,
        y: Float
    ): ControllerState {
        return state.copy(
            rightStickX = x.coerceIn(-1f, 1f),
            rightStickY = y.coerceIn(-1f, 1f)
        )
    }
}
