package it.al.blockbreakerworld

import android.util.Log

class Ball(position: Vec2 = Vec2(), size: Vec2 = Vec2(1f, 1f)): Entity(position, size) {
    val speed: Float = 250f
    var direction = Vec2(1f, 1f)
    var player: Player? = null
    var blockGrid: BlockGrid? = null

    override fun onInit() {

    }

    override fun onUpdate(deltaTime: Float) {
        val p = player

        if(p != null) {
            val collision = checkCollision(this, Vec2(speed * deltaTime * direction.x, speed * deltaTime * direction.y), p)
            if(collision.x != 0f) direction.x *= -1
            if(collision.y != 0f) direction.y *= -1
        }

        val g = blockGrid
        if(g != null) {
            for(e in g.blocks) {
                if(e.destroyed) continue

                val collision = checkCollision(this, Vec2(speed * deltaTime * direction.x, speed * deltaTime * direction.y), e)

                if (collision.x != 0f) { direction.x *= -1; e.destroyed = true; break}
                if (collision.y != 0f) { direction.y *= -1; e.destroyed = true; break}
            }
        }

        if(position.x + scale.x >= pxToDp(ScreenMetrics.width.toFloat())) direction.x = -1f
        if(position.x <= 0) direction.x = 1f

        if(position.y >= pxToDp(ScreenMetrics.height.toFloat())) direction.y = -1f
        if(position.y <= 0) direction.y = 1f

        position.x += speed * deltaTime * direction.x
        position.y += speed * deltaTime * direction.y

    }
}