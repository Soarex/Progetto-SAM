package it.al.blockbreakerworld.game

import android.util.Log
import kotlinx.coroutines.*

class BlockGrid {
    val horizontalBlockCount: Int = 10
    val verticalBlockCount: Int = 15
    lateinit var blocks: Array<Block>
    val height: Float = 300f

    fun onInit() {
        val blockSize = Vec2(
            pxToDp(ScreenMetrics.width.toFloat()) / horizontalBlockCount,
            height / verticalBlockCount
        )

        CoroutineScope(Dispatchers.IO).launch {
            val parser = BlocksJsonParser()
            if(!parser.downloadJson(Game.levelUrl)) {
                Game.onError()
                Game.gameOver = true
                return@launch
            }

            withContext(Dispatchers.Default) {
                Game.brickCount = -1
                blocks = Array(horizontalBlockCount * verticalBlockCount) {i -> Block(
                    Vec2(
                        ((i % horizontalBlockCount) * blockSize.x) + blockSize.x / 2,
                        ((i / horizontalBlockCount) * blockSize.y) + blockSize.y / 2 + 1
                    ), Vec2(blockSize.x - 3, blockSize.y - 3)).apply {
                        val c = parser.getBlock(i)
                        if(c != 0) {
                            color = BlockColors.colors[c - 1]
                            destroyed = false
                            Game.brickCount++
                        }
                        else destroyed = true
                        onInit()
                }}
                Game.brickCount++
            }
        }
    }
}