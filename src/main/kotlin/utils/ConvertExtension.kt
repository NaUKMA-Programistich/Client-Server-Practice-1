package utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import models.Message
import java.nio.ByteBuffer

object ConvertExtension {

    fun Message.toBytes(): ByteArray {
        val mapper = jacksonObjectMapper()

        val informationBytes = mapper.writeValueAsBytes(data)
        val byteBuffer = ByteBuffer.allocate(4 + 4 + informationBytes.size)

        byteBuffer.apply {
            putInt(commandId)
            putInt(userId)
            put(informationBytes)
        }

        return byteBuffer.array()
    }

    fun ByteArray.toMessage(): Message {
        val mapper = jacksonObjectMapper()
        val dataBytes = this.copyOfRange(8, size)

        val byteBuffer = ByteBuffer.wrap(this)

        return Message(
            commandId = byteBuffer.int,
            userId = byteBuffer.int,
            data = mapper.readValue(dataBytes)
        )
    }
}
