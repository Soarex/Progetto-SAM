package it.al.blockbreakerworld.game

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class BlocksJsonParser(){
    private lateinit var jsonDocument: String
    private lateinit var blocks: JSONArray
    private var currentIndex = 0


    fun downloadJson(url: String): Boolean {
        currentIndex = 0

        val urlConnection = URL(url).openConnection() as HttpsURLConnection
        try {
            val ins = BufferedInputStream(urlConnection.inputStream).reader()
            jsonDocument = ins.readText()
        }catch(e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            urlConnection.disconnect()
        }

        val o = JSONObject(jsonDocument)
        blocks = o.getJSONArray("blocks")
        return true
    }

    fun getBlock(i: Int): Int {
        return blocks[i] as Int
    }

}