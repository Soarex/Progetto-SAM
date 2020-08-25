package it.al.blockbreakerworld.game

import android.content.Context

class Game(context: Context) {
    private val _gameSurface = GameSurface(context)

    val gameSurface
        get() = _gameSurface
}