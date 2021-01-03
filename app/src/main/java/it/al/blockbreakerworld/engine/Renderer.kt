package it.al.blockbreakerworld.engine

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Rect
import androidx.core.content.ContextCompat
import java.util.*

class Renderer(private val context: Context) {
    private val entities: Deque<Entity> = LinkedList<Entity>()
    private val paint = Paint()
    private val rect = Rect()

    fun onDraw(canvas: Canvas) {
        for(entity in entities) {
            rect.set(dpToPx(entity.position.x - entity.scale.x / 2),
                dpToPx(entity.position.y - entity.scale.y / 2),
                dpToPx(entity.position.x + entity.scale.x / 2),
                dpToPx(entity.position.y + entity.scale.y / 2))

            paint.color = entity.color
            if(entity.sprite != null) {
                val s = entity.sprite!!
                val spriteDrawable = ContextCompat.getDrawable(context, s)
                spriteDrawable?.bounds = rect
                spriteDrawable?.setColorFilter(entity.color, PorterDuff.Mode.MULTIPLY)
                spriteDrawable?.draw(canvas)
            } else if(entity.text != null) {
                val s = entity.text!!
                paint.textSize = entity.scale.x
                canvas.drawText(s, entity.position.x, entity.position.y, paint)
            } else {
                canvas.drawRect(rect, paint)
            }
        }

        entities.clear()
    }

    fun addEntity(entity: Entity) {
        entities.add(entity)
    }
}