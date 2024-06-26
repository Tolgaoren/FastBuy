package com.toren.producthub.di

import android.app.Application
import androidx.room.Room
import com.toren.producthub.data.local.dao.LikeDao
import com.toren.producthub.data.local.dao.SearchHistoryDao
import com.toren.producthub.data.local.dao.VisitHistoryDao
import com.toren.producthub.data.local.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): LocalDatabase {
        return Room.databaseBuilder(app, LocalDatabase::class.java, "local_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLikeDao(db: LocalDatabase): LikeDao {
        return db.likeDao()
    }

    @Provides
    fun provideSearchHistoryDao(db: LocalDatabase): SearchHistoryDao {
        return db.searchHistoryDao()
    }

    @Provides
    fun provideVisitHistoryDao(db: LocalDatabase): VisitHistoryDao {
        return db.visitHistoryDao()
    }
}