package it.al.blockbreakerworld

import android.graphics.Color

class Player(position: Vec2 = Vec2(pxToDp(ScreenMetrics.width / 2f), pxToDp(ScreenMetrics.height.toFloat() - 100)), size: Vec2 = Vec2(500f, 200f)): Entity(position, size,
    Color.rgb(0, 100, 255)
) {
    var speed = 500f

    override fun onInit() {
        position = Vec2(pxToDp(ScreenMetrics.width.toFloat()) /  2, pxToDp(ScreenMetrics.height.toFloat() - 300))
        scale = Vec2(100f, 20f)

        Input.touchPosition.x = position.x
        Input.touchPosition.y = position.y
    }

    override fun onUpdate(deltaTime: Float) {
        if(position.x > Input.touchPosition.x)
            position.x -= speed * deltaTime

        if(position.x < Input.touchPosition.x)
            position.x += speed * deltaTime
    }
}