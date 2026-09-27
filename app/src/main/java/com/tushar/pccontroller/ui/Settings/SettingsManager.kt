package com.tushar.pccontroller.ui.Settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(
    name = "pc_controller_settings"
)

data class ControllerSettings(

    // =========================
    // CONNECTION
    // =========================

    val pcIp: String = "192.168.1.100",

    val udpPort: Int = 5000,

    // =========================
    // JOYSTICK
    // =========================

    val leftSensitivity: Float = 1.0f,

    val rightSensitivity: Float = 1.0f,

    val joystickDeadzone: Float = 0.05f,

    // =========================
    // GYRO
    // =========================

    val gyroEnabled: Boolean = false,

    val gyroSensitivity: Float = 1.0f,

    val gyroXSensitivity: Float = 1.0f,

    val gyroYSensitivity: Float = 1.0f,

    val gyroInvertX: Boolean = false,

    val gyroInvertY: Boolean = false,

    val gyroDeadzone: Float = 0.05f,

    val gyroSmoothing: Float = 0.20f,

    // =========================
    // APPEARANCE
    // =========================

    val buttonOpacity: Float = 1.0f,

    val controllerLayout: String = "XBOX"
)

class SettingsManager(
    context: Context
) {

    private val appContext = context.applicationContext

    // =========================
    // OBSERVE SETTINGS
    // =========================

    val settings: Flow<ControllerSettings> =
        appContext.settingsDataStore.data.map { preferences ->

            ControllerSettings(

                // Connection
                pcIp = preferences[Keys.PC_IP]
                    ?: DEFAULT_SETTINGS.pcIp,

                udpPort = preferences[Keys.UDP_PORT]
                    ?: DEFAULT_SETTINGS.udpPort,

                // Joystick
                leftSensitivity = preferences[Keys.LEFT_SENSITIVITY]
                    ?: DEFAULT_SETTINGS.leftSensitivity,

                rightSensitivity = preferences[Keys.RIGHT_SENSITIVITY]
                    ?: DEFAULT_SETTINGS.rightSensitivity,

                joystickDeadzone = preferences[Keys.JOYSTICK_DEADZONE]
                    ?: DEFAULT_SETTINGS.joystickDeadzone,

                // Gyro
                gyroEnabled = preferences[Keys.GYRO_ENABLED]
                    ?: DEFAULT_SETTINGS.gyroEnabled,

                gyroSensitivity = preferences[Keys.GYRO_SENSITIVITY]
                    ?: DEFAULT_SETTINGS.gyroSensitivity,

                gyroXSensitivity = preferences[Keys.GYRO_X_SENSITIVITY]
                    ?: DEFAULT_SETTINGS.gyroXSensitivity,

                gyroYSensitivity = preferences[Keys.GYRO_Y_SENSITIVITY]
                    ?: DEFAULT_SETTINGS.gyroYSensitivity,

                gyroInvertX = preferences[Keys.GYRO_INVERT_X]
                    ?: DEFAULT_SETTINGS.gyroInvertX,

                gyroInvertY = preferences[Keys.GYRO_INVERT_Y]
                    ?: DEFAULT_SETTINGS.gyroInvertY,

                gyroDeadzone = preferences[Keys.GYRO_DEADZONE]
                    ?: DEFAULT_SETTINGS.gyroDeadzone,

                gyroSmoothing = preferences[Keys.GYRO_SMOOTHING]
                    ?: DEFAULT_SETTINGS.gyroSmoothing,

                // Appearance
                buttonOpacity = preferences[Keys.BUTTON_OPACITY]
                    ?: DEFAULT_SETTINGS.buttonOpacity,

                controllerLayout = preferences[Keys.CONTROLLER_LAYOUT]
                    ?: DEFAULT_SETTINGS.controllerLayout
            )
        }

    // =========================
    // CONNECTION
    // =========================

    suspend fun setPcIp(ip: String) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.PC_IP] = ip.trim()
        }
    }

    suspend fun setUdpPort(port: Int) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.UDP_PORT] =
                port.coerceIn(1, 65535)
        }
    }

    // =========================
    // JOYSTICK
    // =========================

    suspend fun setLeftSensitivity(value: Float) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.LEFT_SENSITIVITY] =
                value.coerceAtLeast(0f)
        }
    }

    suspend fun setRightSensitivity(value: Float) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.RIGHT_SENSITIVITY] =
                value.coerceAtLeast(0f)
        }
    }

    suspend fun setJoystickDeadzone(value: Float) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.JOYSTICK_DEADZONE] =
                value.coerceIn(0f, 0.99f)
        }
    }

    // =========================
    // GYRO
    // =========================

    suspend fun setGyroEnabled(enabled: Boolean) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.GYRO_ENABLED] = enabled
        }
    }

    suspend fun setGyroSensitivity(value: Float) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.GYRO_SENSITIVITY] =
                value.coerceAtLeast(0f)
        }
    }

    suspend fun setGyroXSensitivity(value: Float) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.GYRO_X_SENSITIVITY] =
                value.coerceAtLeast(0f)
        }
    }

    suspend fun setGyroYSensitivity(value: Float) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.GYRO_Y_SENSITIVITY] =
                value.coerceAtLeast(0f)
        }
    }

    suspend fun setGyroInvertX(invert: Boolean) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.GYRO_INVERT_X] = invert
        }
    }

    suspend fun setGyroInvertY(invert: Boolean) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.GYRO_INVERT_Y] = invert
        }
    }

    suspend fun setGyroDeadzone(value: Float) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.GYRO_DEADZONE] =
                value.coerceIn(0f, 0.99f)
        }
    }

    suspend fun setGyroSmoothing(value: Float) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.GYRO_SMOOTHING] =
                value.coerceIn(0f, 0.99f)
        }
    }

    // =========================
    // APPEARANCE
    // =========================

    suspend fun setButtonOpacity(value: Float) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.BUTTON_OPACITY] =
                value.coerceIn(0f, 1f)
        }
    }

    suspend fun setControllerLayout(layout: String) {

        appContext.settingsDataStore.edit { preferences ->
            preferences[Keys.CONTROLLER_LAYOUT] =
                layout
        }
    }

    // =========================
    // RESET
    // =========================

    suspend fun resetSettings() {

        appContext.settingsDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // =========================
    // DEFAULT SETTINGS
    // =========================

    companion object {

        val DEFAULT_SETTINGS = ControllerSettings()
    }

    // =========================
    // DATASTORE KEYS
    // =========================

    private object Keys {

        // Connection
        val PC_IP =
            stringPreferencesKey("pc_ip")

        val UDP_PORT =
            intPreferencesKey("udp_port")

        // Joystick
        val LEFT_SENSITIVITY =
            floatPreferencesKey("left_sensitivity")

        val RIGHT_SENSITIVITY =
            floatPreferencesKey("right_sensitivity")

        val JOYSTICK_DEADZONE =
            floatPreferencesKey("joystick_deadzone")

        // Gyro
        val GYRO_ENABLED =
            booleanPreferencesKey("gyro_enabled")

        val GYRO_SENSITIVITY =
            floatPreferencesKey("gyro_sensitivity")

        val GYRO_X_SENSITIVITY =
            floatPreferencesKey("gyro_x_sensitivity")

        val GYRO_Y_SENSITIVITY =
            floatPreferencesKey("gyro_y_sensitivity")

        val GYRO_INVERT_X =
            booleanPreferencesKey("gyro_invert_x")

        val GYRO_INVERT_Y =
            booleanPreferencesKey("gyro_invert_y")

        val GYRO_DEADZONE =
            floatPreferencesKey("gyro_deadzone")

        val GYRO_SMOOTHING =
            floatPreferencesKey("gyro_smoothing")

        // Appearance
        val BUTTON_OPACITY =
            floatPreferencesKey("button_opacity")

        val CONTROLLER_LAYOUT =
            stringPreferencesKey("controller_layout")
    }
}