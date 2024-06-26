package com.toren.producthub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toren.producthub.data.local.entity.Like

@Dao
interface LikeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLike(like: Like): Long

    @Query("SELECT * FROM likes WHERE user_id = :userId AND product_id = :productId")
    suspend fun getLike(userId: Int, productId: Int): Like?

    @Delete
    suspend fun deleteLike(like: Like)

    @Query("SELECT * FROM likes WHERE user_id = :userId")
    suspend fun getLikesByUserId(userId: Int): List<Like>
}