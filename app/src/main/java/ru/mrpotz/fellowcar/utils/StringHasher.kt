package ru.mrpotz.fellowcar.utils

import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

object StringHasher {
    fun getStringHash(input: CharSequence): String? {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            var hash = charsToBytes(input)
            hash = digest.digest(hash)
            encodeHexString(hash)
        } catch (e: NoSuchAlgorithmException) {
            return null;
        }
    }

    private fun charsToBytes(chars: CharSequence): ByteArray {
        val charset: Charset = Charset.forName("UTF-8")
        val byteBuffer: ByteBuffer = charset.encode(CharBuffer.wrap(chars))
        return Arrays.copyOf(byteBuffer.array(), byteBuffer.limit())
    }

    private fun encodeHexString(byteArray: ByteArray): String {
        val hexStringBuffer = StringBuilder()
        for (b in byteArray) {
            hexStringBuffer.append(byteToHex(b))
        }
        return hexStringBuffer.toString()
    }

    private fun byteToHex(num: Byte): String {
        val numInt = num.toInt() and 0xFF
        val hexDigits = CharArray(2)
        hexDigits[0] = Character.forDigit(numInt.ushr(4), 16)
        hexDigits[1] = Character.forDigit(numInt and 0x0F, 16)
        return String(hexDigits)
    }
}


