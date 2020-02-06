package com.example.myapplication.apiclient.model

import android.util.Base64
import com.google.gson.annotations.SerializedName
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class JWTToken() {
    @SerializedName("jwttoken")
    var text: String? = null
}

class UsernamePasswordParams(username: String, password: String)

object JWTUtils {
    @Throws(Exception::class)
    fun decoded(JWTEncoded: String) : String {
        try {
            val split = JWTEncoded.split("\\.").toTypedArray()
            val body = getJson(split[0])
            return body
        } catch (e: UnsupportedEncodingException) { //Error
        }
        return "";
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, Charset.forName("UTF-8"))
    }
}