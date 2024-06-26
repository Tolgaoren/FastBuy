package com.toren.producthub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toren.producthub.data.local.entity.VisitHistory

@Dao
interface VisitHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisitHistory(visitHistory: VisitHistory) : Long

    @Query("SELECT * FROM visit_history WHERE user_id = :userId")
    suspend fun getVisitHistoriesByUserId(userId: Int): List<VisitHistory>

}