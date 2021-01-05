package it.al.blockbreakerworld.map

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class LevelListJsonParser(){
    private lateinit var jsonDocument: String
    private lateinit var levels: JSONArray
    private var _levelCount = 0

    val levelCount
    get() = _levelCount

    fun downloadJson(url: String): Boolean {
        _levelCount = 0

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

        levels = JSONArray(jsonDocument)
        _levelCount = levels.length()
        return true
    }

    fun getLevel(i: Int): Level {
        val res = Level()
        val o = levels[i] as JSONObject
        res.id = o.getInt("id")
        res.title = o.getString("title")
        res.description = o.getString("description")
        res.lat = o.getDouble("lat")
        res.lng = o.getDouble("lng")
        res.url = o.getString("url")
        return res
    }

}