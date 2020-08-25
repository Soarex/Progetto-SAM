package it.al.blockbreakerworld.game

import android.graphics.Color.rgb
import android.graphics.drawable.Drawable

abstract class Entity (
    var position: Vec2 = Vec2(),
    var scale: Vec2 = Vec2(
        1f,
        1f
    ),
    var color: Int = rgb(255, 255, 255),
    var sprite: Drawable? = null
) {
    abstract fun onInit()
    abstract fun onUpdate(deltaTime: Float)
}