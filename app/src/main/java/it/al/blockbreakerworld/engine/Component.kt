package it.al.blockbreakerworld.engine

abstract class Component {
    lateinit var entity: Entity

    abstract fun init()
    abstract fun update()
}