package it.al.blockbreakerworld.engine

object EngineEvents {
    private val onInitListeners = mutableListOf<() -> Unit>()

    fun onInit() {
        for(l in onInitListeners)
            l.invoke()
    }

    fun addOnInitListener(listener: () -> Unit) {
        onInitListeners.add(listener)
    }

    fun removeOnInitListener(listener: () -> Unit) {
        onInitListeners.remove(listener)
    }
}