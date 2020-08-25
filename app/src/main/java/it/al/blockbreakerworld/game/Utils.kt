package it.al.blockbreakerworld.game

fun checkCollision(dynamicEntity: Entity, speed: Vec2, staticEntity: Entity): Vec2 {
    val e1 = dynamicEntity; val e2 = staticEntity
    val res = Vec2()

    if (e1.position.x + e1.scale.x / 2 + speed.x > e2.position.x - e2.scale.x / 2 &&
        e1.position.x - e1.scale.x / 2 + speed.x  < e2.position.x + e2.scale.x / 2 &&
        e1.position.y + e1.scale.y / 2 > e2.position.y - e2.scale.y / 2 &&
        e1.position.y - e1.scale.y / 2 < e2.position.y + e2.scale.y / 2) res.x = 1f

    if (e1.position.x + e1.scale.x / 2 > e2.position.x - e2.scale.x / 2 &&
        e1.position.x - e1.scale.x / 2 < e2.position.x + e2.scale.x / 2 &&
        e1.position.y + e1.scale.y / 2 + speed.y > e2.position.y - e2.scale.y / 2 &&
        e1.position.y - e1.scale.y / 2 + speed.y < e2.position.y + e2.scale.y / 2) {
        res.y = 1f
    }

    return res
}

fun dpToPx(dp: Float): Float {
    return dp * ScreenMetrics.density
}

fun pxToDp(px: Float): Float {
    return px / ScreenMetrics.density
}