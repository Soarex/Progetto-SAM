package it.al.blockbreakerworld.engine

object SceneManager {
    private var currentScene: Scene = EmptyScene()

    fun loadNewScene(newScene: Scene) {
        EntityManager.clear()
        Input.clearListeners()
        newScene.load()
        currentScene = newScene

        for(e in EntityManager.getEntities())
            e.init()
    }

    fun reloadScene() {
        loadNewScene(currentScene)
    }
}