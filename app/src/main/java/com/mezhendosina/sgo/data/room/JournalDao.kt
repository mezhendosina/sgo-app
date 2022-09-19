package com.mezhendosina.sgo.data.room

import androidx.paging.PagingSource
import androidx.room.*
import com.mezhendosina.sgo.data.room.entities.JournalRoomEntity

@Dao
interface JournalDao {

    @Query("SELECT * FROM journal ORDER BY time")
    fun getWeek(): PagingSource<Int, JournalRoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(weeks: JournalRoomEntity)

    @Query("DELETE FROM journal WHERE time = :time")
    suspend fun clear(time: Int)

    @Transaction
    suspend fun refresh(time: Int, weeks: JournalRoomEntity) {
        clear(time)
        save(weeks)
    }
}