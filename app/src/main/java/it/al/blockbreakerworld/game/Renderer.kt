package it.al.blockbreakerworld.game

import android.graphics.Canvas
import android.graphics.Paint
import java.util.*

class Renderer {
    private val entities: Deque<Entity> = LinkedList<Entity>()
    private val paint = Paint()

    fun onDraw(canvas: Canvas) {
        for(entity in entities) {
            paint.color = entity.color
            canvas.drawRect(
                dpToPx(entity.position.x - entity.scale.x / 2),
                dpToPx(entity.position.y - entity.scale.y / 2),
                dpToPx(entity.position.x + entity.scale.x / 2),
                dpToPx(entity.position.y + entity.scale.y / 2), paint)
        }

        entities.clear()
    }

    fun addEntity(entity: Entity) {
        entities.add(entity)
    }
}