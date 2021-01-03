package it.al.blockbreakerworld.engine

import android.util.Log
import java.util.*

object EntityManager {
    private val entities = Collections.synchronizedList(LinkedList<Entity>())//mutableListOf<Entity>()

    fun createEntity(): Entity {
        val res = Entity()
        entities.add(res)
        return res
    }

    fun getEntities(): List<Entity> {
        return entities
    }

    fun findEntitiesWithTag(tag: String): List<Entity> {
        return entities.filter { it.tag == tag }
    }

    fun removeEntity(entity: Entity) {
        entities.remove(entity)
    }

    fun removeEntity(index: Int) {
        entities.removeAt(index)
    }

    fun clear() {
        entities.clear()
    }
}