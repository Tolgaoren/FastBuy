package com.toren.producthub.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toren.producthub.data.local.dao.LikeDao
import com.toren.producthub.data.local.dao.SearchHistoryDao
import com.toren.producthub.data.local.dao.VisitHistoryDao
import com.toren.producthub.data.local.entity.Like
import com.toren.producthub.data.local.entity.SearchHistory
import com.toren.producthub.data.local.entity.VisitHistory

@Database(entities = [Like::class, SearchHistory::class, VisitHistory::class], version = 3, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun likeDao(): LikeDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun visitHistoryDao(): VisitHistoryDao
}