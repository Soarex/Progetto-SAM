package it.al.blockbreakerworld.game

import it.al.blockbreakerworld.engine.*

class TargetController : Component() {
    var speed: Float = 200f
    var direction = Vec2(1f, 1f)

    lateinit var health: Entity

    override fun init() {
        health = EntityManager.findEntitiesWithTag("Health")[0]
    }

    override fun update() {
        entity.position.x += speed * direction.x * Time.deltaTime
        entity.position.y += speed * direction.y * Time.deltaTime

        if(entity.position.x > pxToDp(ScreenMetrics.width.toFloat()) + 60 || entity.position.x < 0 - 60) {
            //EntityManager.removeEntity(entity)
            //entity.enabled = false
            Game.currentMisses++
            Game.targetCount--
            val percent = ((1 - Game.currentMisses / Game.maxMisses.toFloat()) * 100).toInt()
            health.text = "$percent%"

            if(Game.maxMisses <= Game.currentMisses) Game.onGameOver()
        }
    }
}