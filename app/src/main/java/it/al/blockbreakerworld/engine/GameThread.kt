package it.al.blockbreakerworld.engine

import android.graphics.Canvas
import android.os.Build
import android.util.Log
import android.view.SurfaceHolder
import androidx.annotation.RequiresApi
import it.al.blockbreakerworld.game.Game

class GameThread(private val gameSurface: GameSurface, private val surfaceHolder: SurfaceHolder) : Thread() {

    private var running = false
    private val FPS = 60
    private val ticksPerSecond = 1000 / FPS

    private var _deltaTime = 0f
    private val renderer = Renderer(gameSurface.context)

    var touchRegistered = false

    override fun run() {
        var startTime = System.nanoTime()
        EngineEvents.onInit()

        while (running) {
            var canvas: Canvas? = null
            try {

                canvas = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) surfaceHolder.lockHardwareCanvas()
                else surfaceHolder.lockCanvas()

                synchronized(canvas) {
                    if(touchRegistered) {
                        touchRegistered = false
                        Input.onTouchBegin()
                    }

                    for(e in EntityManager.getEntities()) {
                        if(!e.enabled) continue

                        e.update()
                        renderer.addEntity(e)
                    }

                    gameSurface.draw(canvas)
                    renderer.onDraw(canvas)
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
            Time.deltaTime = _deltaTime
            startTime = System.nanoTime()
        }
    }

    fun setRunning(running: Boolean) {
        this.running = running
    }

}