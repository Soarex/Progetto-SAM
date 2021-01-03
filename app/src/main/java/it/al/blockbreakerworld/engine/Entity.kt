package it.al.blockbreakerworld.engine

import android.graphics.Color.rgb
import android.graphics.drawable.Drawable

class Entity (
    var tag: String = "",
    var position: Vec2 = Vec2(),
    var scale: Vec2 = Vec2(
        1f,
        1f
    ),
    var color: Int = rgb(255, 255, 255),
    var sprite: Int? = null,
    var text: String? = null,
    var enabled: Boolean = true
) {
    private val components = mutableListOf<Component>()

    fun init() {
        for (c in components)
            c.init()
    }

    fun update() {
        for (c in components)
            c.update()
    }

    fun addComponent(component: Component) {
        component.entity = this
        components.add(component)
    }

    fun removeComponent(component: Component) {
        components.remove(component)
    }

    override fun toString(): String {
        return "Position: (${position.x}, ${position.y})"
    }
}