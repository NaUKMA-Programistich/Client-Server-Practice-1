package utils

import utils.CipherUtil.decode
import utils.CipherUtil.encode
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CipherUtil {

    private const val secretBytes = "AlexAlexAlexAlex"
    private val key = SecretKeySpec(secretBytes.toByteArray(StandardCharsets.UTF_8), "AES")
    private val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

    private fun init(mode: Int) {
        cipher.init(mode, key, IvParameterSpec(ByteArray(16)))
    }

    fun ByteArray.decode(): ByteArray {
        init(Cipher.DECRYPT_MODE)
        return cipher.doFinal(this)
    }

    fun ByteArray.encode(): ByteArray {
        init(Cipher.ENCRYPT_MODE)
        return cipher.doFinal(this)
    }
}

fun main() {
    val bytes = byteArrayOf(1, 2, 3)
    println(bytes.encode().contentToString())
    println(bytes.encode().decode().contentToString())
}
