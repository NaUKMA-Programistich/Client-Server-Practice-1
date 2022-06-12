import convertors.Decoder
import convertors.Encoder
import models.Message
import models.Packet
import org.junit.jupiter.api.assertThrows
import utils.PacketExtension
import kotlin.test.Test
import kotlin.test.assertEquals

class ConvertorTest {

    private val mockMessage = Message(
        commandId = 7,
        userId = 1,
        data = "Hello"
    )
    private val mockPacket = Packet(
        clientId = 1,
        packetId = 8,
        message = mockMessage
    )

    private val tmpBytes = byteArrayOf(19, 1, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 16, 95, -16, -8, -36, 101, 20, 125, -93, -101, -98, -74, 33, 8, 92, -8, 7, 81, 47, 74, 107)

    @Test
    fun `Encoder Packet`() {
        val encoder = Encoder(packet = mockPacket)
        val bytes = encoder.result()
        println(bytes.contentToString())
    }

    @Test
    fun `Encoder Packet and check magic byte`() {
        val encoder = Encoder(packet = mockPacket)
        val bytes = encoder.result()
        assertEquals(bytes[0], 0x13)
    }

    @Test
    fun `Decoder bytes`() {
        val decoder = Decoder(byteArray = tmpBytes)
        val packet = decoder.result()
        println(packet)
    }

    @Test
    fun `Encoder packet and encode bytes`() {
        val encoder = Encoder(packet = mockPacket)
        val bytes = encoder.result()
        val decoder = Decoder(byteArray = bytes)
        val packet = decoder.result()
        assertEquals(mockPacket, packet)
    }

    @Test
    fun `Encoder packet and decoder bytes with change magic byte`() {
        val encoder = Encoder(packet = mockPacket)
        val bytes = encoder.result()
        bytes[0] = 0x15
        assertThrows<PacketExtension> {
            val decoder = Decoder(byteArray = bytes)
            val packet = decoder.result()
            println(packet)
        }
    }

    @Test
    fun `Encoder packet and decoder bytes with change CRC`() {
        val encoder = Encoder(packet = mockPacket)
        val bytes = encoder.result()
        bytes[3] = 0x15
        assertThrows<PacketExtension> {
            val decoder = Decoder(byteArray = bytes)
            val packet = decoder.result()
            println(packet)
        }
    }
}
