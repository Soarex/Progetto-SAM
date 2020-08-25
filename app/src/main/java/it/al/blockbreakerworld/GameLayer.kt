package it.al.blockbreakerworld

import android.graphics.Canvas

interface GameLayer {
    fun onInit()
    fun onUpdate(deltaTime: Float)
    fun onDraw(canvas: Canvas)
}