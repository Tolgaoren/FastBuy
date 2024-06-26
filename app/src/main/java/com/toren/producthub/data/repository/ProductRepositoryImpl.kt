package com.toren.producthub.data.repository

import com.toren.producthub.data.remote.api.ProductApi
import com.toren.producthub.data.remote.dto.auth.AuthRequest
import com.toren.producthub.data.remote.dto.auth.AuthResponse
import com.toren.producthub.data.remote.dto.cart.AddCartRequest
import com.toren.producthub.data.remote.dto.cart.AddCartResponse
import com.toren.producthub.data.remote.dto.cart.CartsByUserDto
import com.toren.producthub.data.remote.dto.category.CategoriesDto
import com.toren.producthub.data.remote.dto.product.ProductResponse
import com.toren.producthub.data.remote.dto.product.ProductsDto
import com.toren.producthub.data.remote.dto.user.UserDto
import com.toren.producthub.domain.model.User
import com.toren.producthub.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl
@Inject constructor(
    private val productApi: ProductApi
) : ProductRepository {
    override suspend fun getProducts(): ProductsDto {
        return productApi.getProducts()
    }

    override suspend fun getProduct(id: Int): ProductResponse {
        return productApi.getProduct(id)
    }

    override suspend fun getCategories(): CategoriesDto {
        return productApi.getCategories()
    }

    override suspend fun getCategoryProducts(category: String): ProductsDto {
        return productApi.getCategoryProducts(category)
    }

    override suspend fun searchProducts(query: String): ProductsDto {
        return productApi.searchProducts(query)
    }

    override suspend fun getUser(id: Int): UserDto {
        return productApi.getUser(id)
    }

    override suspend fun updateUser(id: Int, user: User): UserDto {
        return productApi.updateUser(id, user)
    }

    override suspend fun login(request: AuthRequest): AuthResponse {
        return productApi.login(request)
    }

    override suspend fun getCartByUser(id: Int): CartsByUserDto {
        return productApi.getCartByUser(id)
    }

    override suspend fun addCart(addCartRequest: AddCartRequest): AddCartResponse {
        return productApi.addCart(addCartRequest)
    }
}