package it.al.blockbreakerworld.engine

import android.graphics.Canvas

interface GameLayer {
    fun onInit()
    fun onUpdate(deltaTime: Float)
    fun onDraw(canvas: Canvas)
}