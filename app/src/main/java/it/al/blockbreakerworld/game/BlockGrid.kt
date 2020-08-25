package it.al.blockbreakerworld.game

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

        blocks = Array(horizontalBlockCount * verticalBlockCount) {i -> Block(
            Vec2(
                ((i % horizontalBlockCount) * blockSize.x) + blockSize.x / 2,
                ((i / horizontalBlockCount) * blockSize.y) + blockSize.y / 2 + 1
            ),
            Vec2(blockSize.x - 3, blockSize.y - 3)
        ).apply { onInit() }}
    }
}