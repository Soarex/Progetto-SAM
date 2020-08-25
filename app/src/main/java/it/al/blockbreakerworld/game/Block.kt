package it.al.blockbreakerworld.game

import kotlin.random.Random

class Block(position: Vec2 = Vec2(), size: Vec2 = Vec2(
    1f,
    1f
)
): Entity(position, size) {
    var destroyed: Boolean = false

    override fun onInit() {
        color = BlockColors.colors[Random.nextInt(3)]
    }

    override fun onUpdate(deltaTime: Float) {

    }
}