package com.toren.producthub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toren.producthub.data.local.entity.SearchHistory

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistory): Long

    @Query("SELECT * FROM search_history WHERE user_id = :userId")
    suspend fun getSearchHistoriesByUserId(userId: Int): List<SearchHistory>

    @Delete
    suspend fun deleteSearchHistory(searchHistory: SearchHistory)
}
