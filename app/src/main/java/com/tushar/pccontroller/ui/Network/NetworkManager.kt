package com.tushar.pccontroller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class NetworkManager(
    private val onConnectionChanged: (Boolean) -> Unit = {},
    private val onError: (Exception) -> Unit = {}
) {

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

    private var socket: DatagramSocket? = null
    private var sendJob: Job? = null

    private var pcAddress: InetAddress? = null
    private var port: Int = DEFAULT_PORT

    @Volatile
    private var connected = false

    fun configure(
        ip: String,
        port: Int = DEFAULT_PORT
    ) {
        this.port = port

        try {
            pcAddress = InetAddress.getByName(ip)
        } catch (e: Exception) {
            pcAddress = null
            connected = false
            onConnectionChanged(false)
            onError(e)
        }
    }

    fun connect() {
        if (connected) return

        sendJob?.cancel()

        sendJob = scope.launch {

            try {
                val address = pcAddress

                if (address == null) {
                    throw IllegalStateException(
                        "PC IP address is not configured"
                    )
                }

                socket?.close()

                socket = DatagramSocket()

                connected = true
                onConnectionChanged(true)

            } catch (e: Exception) {

                connected = false
                onConnectionChanged(false)
                onError(e)
            }
        }
    }

    fun disconnect() {

        sendJob?.cancel()
        sendJob = null

        try {
            socket?.close()
        } catch (_: Exception) {
        }

        socket = null

        connected = false
        onConnectionChanged(false)
    }

    fun isConnected(): Boolean {
        return connected
    }

    fun sendPacket(packet: ControllerPacket) {

        if (!connected) return

        val address = pcAddress ?: return
        val currentSocket = socket ?: return

        scope.launch {

            try {

                val data = packet.toByteArray()

                val datagram = DatagramPacket(
                    data,
                    data.size,
                    address,
                    port
                )

                currentSocket.send(datagram)

            } catch (e: Exception) {

                connected = false
                onConnectionChanged(false)
                onError(e)
            }
        }
    }

    fun shutdown() {

        disconnect()

        scope.cancel()
    }

    companion object {
        const val DEFAULT_PORT = 5000
    }
}