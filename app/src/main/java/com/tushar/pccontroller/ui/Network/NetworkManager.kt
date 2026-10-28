
package com.tushar.pccontroller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicBoolean

class NetworkManager(
    private val onConnectionChanged: (Boolean) -> Unit = {},
    private val onError: (Exception) -> Unit = {}
) {
    private val scope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val sending = AtomicBoolean(false)

    @Volatile
    private var socket: DatagramSocket? = null

    @Volatile
    private var pcAddress: InetAddress? = null

    @Volatile
    private var port: Int = DEFAULT_PORT

    @Volatile
    private var connected = false

    @Synchronized
    fun configure(ip: String, port: Int = DEFAULT_PORT) {
        require(port in 1..65535) { "Invalid UDP port" }

        try {
            pcAddress = InetAddress.getByName(ip)
            this.port = port
        } catch (e: Exception) {
            pcAddress = null
            onError(e)
        }
    }

    @Synchronized
    fun connect() {
        if (connected) return

        val address = pcAddress
        if (address == null) {
            onError(
                IllegalStateException(
                    "Configure the PC IP address first"
                )
            )
            return
        }

        scope.launch {
            try {
                val newSocket = DatagramSocket()
                newSocket.connect(address, port)

                synchronized(this@NetworkManager) {
                    socket?.close()
                    socket = newSocket
                    connected = true
                }

                onConnectionChanged(true)
            } catch (e: Exception) {
                connected = false
                onConnectionChanged(false)
                onError(e)
            }
        }
    }

    fun sendPacket(packet: ControllerPacket) {
        if (!connected || !sending.compareAndSet(false, true)) {
            return
        }

        scope.launch {
            try {
                val currentSocket = socket ?: return@launch
                if (currentSocket.isClosed) return@launch

                val data = packet.toByteArray()
                val datagram = DatagramPacket(data, data.size)

                currentSocket.send(datagram)
            } catch (e: Exception) {
                connected = false
                onConnectionChanged(false)
                onError(e)
            } finally {
                sending.set(false)
            }
        }
    }

    @Synchronized
    fun disconnect() {
        connected = false
        socket?.close()
        socket = null
        onConnectionChanged(false)
    }

    fun isConnected(): Boolean = connected

    fun shutdown() {
        disconnect()
        scope.cancel()
    }

    companion object {
        const val DEFAULT_PORT = 5000
    }
}
