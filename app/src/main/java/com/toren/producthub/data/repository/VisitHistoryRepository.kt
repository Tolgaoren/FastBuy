package com.toren.producthub.data.repository

import com.toren.producthub.data.local.dao.VisitHistoryDao
import com.toren.producthub.data.local.entity.VisitHistory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisitHistoryRepository
@Inject constructor(
    private val visitHistoryDao: VisitHistoryDao
){
    suspend fun insertVisitHistory(visitHistory: VisitHistory): Long {
        return visitHistoryDao.insertVisitHistory(visitHistory)
    }

    suspend fun getVisitHistoriesByUserId(userId: Int): List<VisitHistory> {
        return visitHistoryDao.getVisitHistoriesByUserId(userId)
    }

}