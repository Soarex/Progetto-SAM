package it.al.blockbreakerworld.game

import android.graphics.Canvas
import android.view.SurfaceHolder
import it.al.blockbreakerworld.game.GameSurface

class GameThread(private val gameSurface: GameSurface, private val surfaceHolder: SurfaceHolder) : Thread() {

    private var running = false
    private val FPS = 60
    private val ticksPerSecond = 1000 / FPS

    private var _deltaTime = 0f
    val deltaTime
    get() = _deltaTime

    override fun run() {
        var startTime = System.nanoTime()

        gameSurface.onInit()
        while (running) {
            var canvas: Canvas? = null
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(canvas) {
                    gameSurface.onUpdate(deltaTime)
                    gameSurface.draw(canvas)
                }
            } catch (e: Exception) {
            } finally {
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas)
            }

            var waitTime = ticksPerSecond - (System.nanoTime() - startTime)
            if (waitTime < 10)
                waitTime = 10

            try {
                sleep(waitTime)
            } catch (e: InterruptedException) {
            }

            _deltaTime = (System.nanoTime() - startTime) / 1000000000f
            startTime = System.nanoTime()
        }
    }

    fun setRunning(running: Boolean) {
        this.running = running
    }

}