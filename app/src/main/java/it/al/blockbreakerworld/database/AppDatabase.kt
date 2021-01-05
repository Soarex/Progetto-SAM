package it.al.blockbreakerworld.database

import androidx.room.Database
import androidx.room.RoomDatabase
import it.al.blockbreakerworld.database.Record
import it.al.blockbreakerworld.database.RecordDao

@Database(entities = arrayOf(Record::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}