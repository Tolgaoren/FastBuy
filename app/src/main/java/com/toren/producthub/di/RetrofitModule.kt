package com.toren.producthub.di

import com.toren.producthub.common.Constants
import com.toren.producthub.data.remote.api.ProductApi
import com.toren.producthub.data.repository.ProductRepositoryImpl
import com.toren.producthub.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideProductApi(): ProductApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productApi: ProductApi): ProductRepository {
        return ProductRepositoryImpl(productApi)
    }
}