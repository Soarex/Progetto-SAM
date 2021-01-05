package it.al.blockbreakerworld.game

import android.graphics.Color
import android.util.Log
import it.al.blockbreakerworld.R
import it.al.blockbreakerworld.engine.*
import kotlinx.coroutines.*

class TargetSpawner: Component() {
    private lateinit var levelInfo: LevelInfo
    private lateinit var targets: Array<Entity>
    private lateinit var spawnPositions: Array<Vec2>

    private var currentTarget = 0
    private var lastSpawnTime = 0L
    private var ready = false

    override fun init() {
        Log.d("Test", "Init")
        CoroutineScope(Dispatchers.IO).launch {
            val parser = LevelJsonParser()
            Log.d("Test", "Downloaded")
            if(!parser.downloadJson(Game.levelUrl)) {
                Game.onError()
                return@launch
            }

            withContext(Dispatchers.Default) {
                levelInfo = parser.getLevelInfo()
                Log.d("Test", "parsed")
                Game.targetCount = levelInfo.targetCount
                Game.maxMisses = levelInfo.maxMisses
                Game.currentMisses = 0

                targets = Array(levelInfo.targetCount) {i ->
                    val target = parser.getTargetInfo(i)
                        EntityManager.createEntity().apply {
                            tag = "Target"
                            position = spawnPositions[target.spawnPosition]
                            scale = Vec2(50f, 50f)
                            color = Color.rgb(255, 255, 255)
                            sprite = R.drawable.sprite_target
                            enabled = false

                            addComponent(TargetController().apply {
                                direction = Vec2(
                                    if(target.spawnPosition <= 2) 1f else -1f,
                                    target.direction.toFloat()
                                )

                                speed = target.speed
                            })
                            init()
                        }
                }
                ready = true
            }
        }

        calculateSpawnPositions()
    }

    override fun update() {
        if(!ready) return
        Log.d("Test", "Ready")
        if(System.currentTimeMillis() - lastSpawnTime > levelInfo.timeBetweenSpawns * 1000) {
            targets[currentTarget].enabled = true
            currentTarget++
            lastSpawnTime = System.currentTimeMillis()
            Log.d("Test", "Spawned")
        }

        if(currentTarget >= levelInfo.targetCount) entity.enabled = false//EntityManager.removeEntity(entity)

    }

    private fun calculateSpawnPositions() {
        val y1 = pxToDp(ScreenMetrics.height * 1/4f)
        val y2 = pxToDp(ScreenMetrics.height * 2/4f)
        val y3 = pxToDp(ScreenMetrics.height * 3/4f)

        spawnPositions = arrayOf(
            Vec2(-55f, y1),
            Vec2(-55f, y2),
            Vec2(-55f, y3),
            Vec2(pxToDp(ScreenMetrics.width.toFloat()) + 55f, y1),
            Vec2(pxToDp(ScreenMetrics.width.toFloat()) + 55f, y2),
            Vec2(pxToDp(ScreenMetrics.width.toFloat()) + 55f, y3)
        )
    }
}
