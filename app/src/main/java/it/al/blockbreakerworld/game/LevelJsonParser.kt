package it.al.blockbreakerworld.game

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class LevelJsonParser(){
    private lateinit var jsonDocument: String
    private lateinit var targets: JSONArray
    private lateinit var levelInfoObject: JSONObject
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

        levelInfoObject = JSONObject(jsonDocument)
        targets = levelInfoObject.getJSONArray("targets")
        return true
    }

    fun getLevelInfo(): LevelInfo {
        return LevelInfo(
            levelInfoObject.getInt("id"),
            levelInfoObject.getInt("target_count"),
            levelInfoObject.getInt("max_misses"),
            levelInfoObject.getDouble("time_between_spawns").toFloat()
        )
    }

    fun getTargetInfo(i: Int): TargetInfo {
        val o = targets[i] as JSONObject
        return TargetInfo(o.getInt("position"), o.getInt("direction"), o.getDouble("speed").toFloat())
    }

}