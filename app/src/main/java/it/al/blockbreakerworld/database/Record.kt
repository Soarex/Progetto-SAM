package it.al.blockbreakerworld.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "level_id") val levelId: Int,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lat") val lat: Double? = null,
    @ColumnInfo(name = "lng") val lng: Double? = null
)