package models

data class Packet(
    val clientId: Byte,
    val packetId: Long,
    val message: Message,
)
