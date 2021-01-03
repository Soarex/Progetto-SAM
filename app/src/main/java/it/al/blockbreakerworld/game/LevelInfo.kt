package it.al.blockbreakerworld.game

data class LevelInfo (
    val id: Int,
    val targetCount: Int,
    val maxMisses: Int,
    val timeBetweenSpawns: Float
)