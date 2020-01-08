package com.example.myapplication

import android.util.Log
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class SentenceActivityFetch {

    // Zmienna dla API_KEY


    fun getUrlBytes(urlSpec: String): ByteArray {
        val url = URL(urlSpec)
        val connection = url.openConnection() as HttpURLConnection

        try {
            val out = ByteArrayOutputStream()
            val input = connection.inputStream

            if(connection.responseCode != HttpURLConnection.HTTP_OK){
                throw IOException(connection.responseMessage)
            }

            var bytesRead: Int
            val buffer = ByteArray(1024)

            do {

                bytesRead = input.read(buffer)
                out.write(buffer, 0, bytesRead)

            } while (input.read(buffer) > 0)
            out.close()

            return out.toByteArray()



        } catch (e: IOException) {
            Log.e("ERROR_HTTP_EXCEPTION", e.message)
            return ByteArray(0)
        }

        finally {
            connection.disconnect()
        }
    }

    fun getUrlString(urlSpec: String): String {
        return String(getUrlBytes(urlSpec))
    }

    fun getJsonString(urlSpec: String): String {

        var jsonString = "Something went wrong"

        try {



            // miejsce na parsa do adresu API

            //
            //
            //  Miejsce na parametry API
            //
            //


        } catch (je: JSONException) {
            Log.e("JSON_ERROR", je.message)
        }
        return jsonString
    }

}