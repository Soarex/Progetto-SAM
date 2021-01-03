package it.al.blockbreakerworld.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

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

fun checkOverlap(e1: Entity, e2: Entity): Boolean {
    if(e1.position.x + e1.scale.x / 2 > e2.position.x - e2.scale.x / 2 &&
        e1.position.x - e1.scale.x / 2 < e2.position.x + e2.scale.x / 2 &&
        e1.position.y + e1.scale.y / 2 > e2.position.y - e2.scale.y / 2 &&
        e1.position.y - e1.scale.y / 2 < e2.position.y + e2.scale.y / 2) return true

    return false
}

fun dpToPx(dp: Float): Int {
    return (dp * ScreenMetrics.density).toInt()
}

fun pxToDp(px: Float): Float {
    return px / ScreenMetrics.density
}

fun getBitmapDescriptor(id: Int, color: Int, context: Context): BitmapDescriptor {
    val vectorDrawable = (AppCompatResources.getDrawable(context, id) as VectorDrawable)

    val h = vectorDrawable.intrinsicHeight
    val w = vectorDrawable.intrinsicWidth

    vectorDrawable.setBounds(0, 0, w, h)

    val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bm)

    vectorDrawable.setTint(color)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
