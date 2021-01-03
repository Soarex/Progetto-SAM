package it.al.blockbreakerworld.game

import it.al.blockbreakerworld.engine.*

class PlayerController: Component() {
    lateinit var score: Entity
    var speed = 500f
    lateinit var hitBox: Entity

    override fun init() {
        score = EntityManager.findEntitiesWithTag("Score").first()
        hitBox = Entity(position = entity.position, scale = Vec2(entity.scale.x / 4, entity.scale.y / 4))

        Input.addOnTouchBeginListener {
            //val toRemove = mutableListOf<Entity>()
            for(e in EntityManager.getEntities())
                if(e.enabled && e.tag == "Target" && checkOverlap(hitBox, e)) {
                    //toRemove.add(e)
                    e.enabled = false
                    Game.targetCount--
                    score.text = (score.text!!.toInt() + 1).toString()
                    break
                }

            //for(e in toRemove)
            //    e.enabled = false//EntityManager.removeEntity(e)

            //toRemove.clear()
        }
    }

    override fun update() {
        entity.position.x -= Input.gyroInput.y * speed * Time.deltaTime
        entity.position.y -= Input.gyroInput.x * speed * Time.deltaTime
        if(Game.targetCount == 0) Game.onWin()
    }
}