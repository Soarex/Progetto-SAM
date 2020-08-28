package it.al.blockbreakerworld.game

import android.content.Context
import android.media.MediaPlayer
import it.al.blockbreakerworld.R

object Game {
    lateinit var context: Context

    var levelUrl: String = ""
    var brickCount: Int = -1
    var gameOver: Boolean = false

    lateinit var brickSoundPlayer: MediaPlayer
    lateinit var gameOverSoundPlayer: MediaPlayer

    private val gameOverCallbacks = MutableList(0) { { ; } }
    private val winCallbacks = MutableList(0) { { ; } }
    private val errorCallbacks = MutableList(0) { { ; } }

    fun addGameOverCallback(callback: () -> Unit) {
        gameOverCallbacks.add(callback)
    }

    fun onGameOver() {
        gameOver = true
        for(c in gameOverCallbacks)
            c.invoke()
    }

    fun addWinCallback(callback: () -> Unit) {
        winCallbacks.add(callback)
    }

    fun onWin() {
        for(c in winCallbacks)
            c.invoke()
    }

    fun addErrorCallback(callback: () -> Unit) {
        errorCallbacks.add(callback)
    }

    fun onError() {
        for(c in errorCallbacks)
            c.invoke()
    }

    fun clear() {
        brickCount = 0
        gameOverCallbacks.clear()
        winCallbacks.clear()
        brickSoundPlayer.release()
        gameOverSoundPlayer.release()
    }
}