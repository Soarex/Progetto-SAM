package it.al.blockbreakerworld.game

import android.graphics.Color
import it.al.blockbreakerworld.R
import it.al.blockbreakerworld.engine.*

class MainScene: Scene {
    override fun load() {
        val player = EntityManager.createEntity().apply {
            tag = "Player"
            position = Vec2(pxToDp(ScreenMetrics.width / 2f), pxToDp(ScreenMetrics.height / 2f))
            scale = Vec2(100f, 100f)
            color = Color.rgb(0, 100, 255)
            sprite = R.drawable.sprite_crosshair
            addComponent(PlayerController())
        }

        val score = EntityManager.createEntity().apply {
            tag = "Score"
            position = Vec2(0 + 50f, 0 + 150f)
            scale = Vec2(100f, 20f)
            text = "0"
        }

        val heart = EntityManager.createEntity().apply {
            position = Vec2(pxToDp(ScreenMetrics.width.toFloat()) - 150f, 0 + 45f)
            scale = Vec2(30f, 30f)
            sprite = R.drawable.sprite_heart
        }

        val health = EntityManager.createEntity().apply {
            tag = "Health"
            position = Vec2(heart.position.x + 500 , 150f)
            scale = Vec2(100f, 30f)
            text = "100%"
        }

        val targetSpawner = EntityManager.createEntity().apply {
            addComponent(TargetSpawner())
        }
    }
}
