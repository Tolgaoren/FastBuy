package com.toren.producthub.data.repository

import com.toren.producthub.data.local.dao.LikeDao
import com.toren.producthub.data.local.entity.Like
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LikeRepository
@Inject constructor(
    private val likeDao: LikeDao
) {
    suspend fun insertLike(like: Like): Long {
        return likeDao.insertLike(like)
    }

    suspend fun getLike(userId: Int, productId: Int): Like? {
        return likeDao.getLike(userId, productId)
    }

    suspend fun deleteLike(like: Like) {
        likeDao.deleteLike(like)
    }

    suspend fun getLikesByUserId(userId: Int): List<Like> {
        return likeDao.getLikesByUserId(userId)
    }
}