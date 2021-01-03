package it.al.blockbreakerworld.engine

object Input {
    val touchPosition = Vec2()
    val gyroInput = Vec2()

    private val onTouchBeginListeners = mutableListOf<() -> Unit>()

    fun onTouchBegin() {
        for(l in onTouchBeginListeners)
            l.invoke()
    }

    fun addOnTouchBeginListener(listener: () -> Unit) {
        onTouchBeginListeners.add(listener)
    }

    fun removeOnTouchBeginListener(listener: () -> Unit) {
        onTouchBeginListeners.remove(listener)
    }

    fun clearListeners() {
        onTouchBeginListeners.clear()
    }
}