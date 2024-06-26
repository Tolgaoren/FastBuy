package com.toren.producthub.data.repository

import com.toren.producthub.data.local.dao.SearchHistoryDao
import com.toren.producthub.data.local.entity.SearchHistory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchHistoryRepository @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) {

    suspend fun insertSearchHistory(searchHistory: SearchHistory): Long {
        return searchHistoryDao.insertSearchHistory(searchHistory)
    }

    suspend fun getSearchHistoriesByUserId(userId: Int): List<SearchHistory> {
        return searchHistoryDao.getSearchHistoriesByUserId(userId)
    }

    suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        searchHistoryDao.deleteSearchHistory(searchHistory)
    }
}
