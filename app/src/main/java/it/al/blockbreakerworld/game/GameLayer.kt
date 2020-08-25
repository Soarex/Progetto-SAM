package it.al.blockbreakerworld.game

import android.graphics.Canvas

interface GameLayer {
    fun onInit()
    fun onUpdate(deltaTime: Float)
    fun onDraw(canvas: Canvas)
}