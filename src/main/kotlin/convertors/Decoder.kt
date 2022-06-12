package convertors

import models.Packet
import utils.CRCExtension.toCRC
import utils.CipherUtil.decode
import utils.ConvertExtension.toMessage
import utils.PacketExtension
import java.nio.ByteBuffer

class Decoder(byteArray: ByteArray) {

    private var packet: Packet

    init {
        val byteBuffer: ByteBuffer = ByteBuffer.wrap(byteArray)

        val firstByte = byteBuffer.get() // 1 byte
        checkFirstCode(byte = firstByte)

        val clientId = byteBuffer.get() // 1 byte
        val packetId = byteBuffer.long // 8 byte
        val packetLen = byteBuffer.int // 4 byte

        val crcPacket = byteBuffer.short // 2 byte
        val headerBytes = byteArray.copyOfRange(0, 14)
        checkCRC(crc = crcPacket, byteArray = headerBytes)

        val messageBytes = ByteArray(packetLen) {
            byteBuffer.get()
        } // 16 + packetLen byte
        val message = messageBytes.decode().toMessage()

        val crcMessage = byteBuffer.short // // 16 + packetLen byte + 2
        checkCRC(crc = crcMessage, byteArray = messageBytes)

        packet = Packet(
            clientId = clientId,
            packetId = packetId,
            message = message
        )
    }

    fun result() = packet

    private fun checkFirstCode(byte: Byte) {
        if (byte.compareTo(0x13) != 0) throw PacketExtension("First byte not valid")
    }

    private fun checkCRC(crc: Short, byteArray: ByteArray) {
        if (byteArray.toCRC() != crc) throw PacketExtension("CRC not valid")
    }
}

fun main() {
    val bytes = byteArrayOf(19, 1, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 16, 95, -16, -8, -36, 101, 20, 125, -93, -101, -98, -74, 33, 8, 92, -8, 7, 81, 47, 74, 107)
    val decoder = Decoder(bytes)
    println(decoder.result())
}
