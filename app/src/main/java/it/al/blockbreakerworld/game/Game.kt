package it.al.blockbreakerworld.game

import android.content.Context
import android.media.MediaPlayer

object Game {
    var levelUrl: String = ""
    var targetCount: Int = -1
    var maxMisses: Int = -1
    var currentMisses: Int = -1

    lateinit var brickSoundPlayer: MediaPlayer
    lateinit var gameOverSoundPlayer: MediaPlayer

    private val gameOverCallbacks = mutableListOf<() -> Unit>()
    private val winCallbacks = mutableListOf<() -> Unit>()
    private val errorCallbacks = mutableListOf<() -> Unit>()

    fun addGameOverCallback(callback: () -> Unit) {
        gameOverCallbacks.add(callback)
    }

    fun onGameOver() {
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
        targetCount = -1
        maxMisses = -1
        currentMisses = -1
        gameOverCallbacks.clear()
        winCallbacks.clear()
    }

    fun release() {
        brickSoundPlayer.release()
        gameOverSoundPlayer.release()
    }
}