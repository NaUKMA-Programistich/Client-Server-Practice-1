package convertors

import models.Message
import models.Packet
import utils.CRCExtension.toCRC
import utils.CipherUtil.encode
import utils.ConvertExtension.toBytes
import java.nio.ByteBuffer

class Encoder(packet: Packet) {

    private var array: ByteArray

    init {
        val messageBytes = packet.message.toBytes().encode()
        val byteBuffer = ByteBuffer.allocate(1 + 1 + 8 + 4 + 2 + messageBytes.size + 2)

        byteBuffer.apply {
            put(0x13) // 1 byte
            put(packet.clientId) // 1 byte
            putLong(packet.packetId) // 8 byte
            putInt(messageBytes.size) // 4 byte
        }

        val headerBytes = byteBuffer.array().copyOfRange(0, 14)

        byteBuffer.apply {
            putShort(headerBytes.toCRC())
            put(messageBytes)
            putShort(messageBytes.toCRC())
        }

        array = byteBuffer.array()
    }

    fun result() = array
}

fun main() {
    val message = Message(
        commandId = 7,
        userId = 1,
        data = "Hello"
    )
    val packet = Packet(
        clientId = 1,
        packetId = 8,
        message = message
    )
    val encoder = Encoder(packet)
    println(encoder.result().contentToString())
}
