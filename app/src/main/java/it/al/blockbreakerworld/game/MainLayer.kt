package it.al.blockbreakerworld.game

import android.graphics.Canvas

class MainLayer : GameLayer {
    private val renderer = Renderer()
    val ball = Ball(
        Vec2(25f, 25f),
        Vec2(15f, 15f)
    )
    val player = Player()
    val blockGrid = BlockGrid()

    override fun onInit() {
        player.onInit()
        ball.position = Vec2(
            player.position.x,
            player.position.y - 25
        )
        ball.onInit()
        blockGrid.onInit()
        ball.player = player
        ball.blockGrid = blockGrid
    }

    override fun onDraw(canvas: Canvas) {
        renderer.addEntity(ball)
        renderer.addEntity(player)

        for(e in blockGrid.blocks)
            if(!e.destroyed)
                renderer.addEntity(e)

        renderer.onDraw(canvas)
    }

    override fun onUpdate(deltaTime: Float) {
        ball.onUpdate(deltaTime)
        player.onUpdate(deltaTime)
    }
}