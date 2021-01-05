package it.al.blockbreakerworld.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDao {
    @Query("SELECT * FROM record")
    fun getAll(): List<Record>

    @Query("SELECT * FROM record WHERE level_id = :levelId")
    fun getAllByLevelId(levelId: Int): List<Record>

    @Query("SELECT * FROM record WHERE level_id = :levelId LIMIT :begin, :count")
    fun getSomeByLevelId(levelId: Int, begin: Int, count: Int): List<Record>

    @Query("SELECT COUNT(*) FROM record WHERE level_id = :levelId")
    fun getRecordCount(levelId: Int): Int

    @Query("SELECT * FROM record WHERE name LIKE :username")
    fun findByName(username: String): List<Record>

    @Insert
    fun insertAll(vararg records: Record)

    @Delete
    fun delete(record: Record)
}