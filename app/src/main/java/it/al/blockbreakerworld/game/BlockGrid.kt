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
            val parser = JsonParser()
            if(!parser.downloadJson("https://api.jsonbin.io/b/5f454393993a2e110d362865"))
                Log.e("Test", "Errore di download")

            //withContext(Dispatchers.Default) {
                blocks = Array(horizontalBlockCount * verticalBlockCount) {i -> Block(
                    Vec2(
                        ((i % horizontalBlockCount) * blockSize.x) + blockSize.x / 2,
                        ((i / horizontalBlockCount) * blockSize.y) + blockSize.y / 2 + 1
                    ), Vec2(blockSize.x - 3, blockSize.y - 3)).apply {
                        val c = parser.getBlock(i)
                        if(c != 0) {
                            color = BlockColors.colors[c - 1]
                            destroyed = false
                        }
                        else destroyed = true
                        onInit()
                }}
            //}
        }
    }
}