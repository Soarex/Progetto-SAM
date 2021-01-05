package it.al.blockbreakerworld.engine

import android.util.Log
import java.util.*

object EntityManager {
    private val entities = LinkedList<Entity>()
    private val added = Collections.synchronizedList(LinkedList<Entity>())//mutableListOf<Entity>()
    private val removed = Collections.synchronizedList(LinkedList<Entity>())//mutableListOf<Entity>()

    fun createEntity(): Entity {
        val res = Entity()
        added.add(res)
        return res
    }

    fun getEntities(): List<Entity> {
        return entities
    }

    fun findEntitiesWithTag(tag: String): List<Entity> {
        return entities.filter { it.tag == tag }
    }

    fun removeEntity(entity: Entity) {
        removed.add(entity)
    }

    fun sync() {
        entities.removeAll(removed)
        entities.addAll(added)
        removed.clear()
        added.clear()

    }

    fun clear() {
        entities.clear()
        added.clear()
        removed.clear()
    }
}