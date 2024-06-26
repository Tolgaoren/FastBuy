package com.toren.producthub.domain.repository

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

interface ProductRepository {

    suspend fun getProducts(): ProductsDto

    suspend fun getProduct(id: Int): ProductResponse

    suspend fun getCategories(): CategoriesDto

    suspend fun getCategoryProducts(category: String) : ProductsDto

    suspend fun searchProducts(query: String) : ProductsDto

    suspend fun getUser(id: Int): UserDto

    suspend fun updateUser(id: Int, user: User) : UserDto

    suspend fun login(request: AuthRequest) : AuthResponse

    suspend fun getCartByUser(id: Int) : CartsByUserDto

    suspend fun addCart(addCartRequest: AddCartRequest) : AddCartResponse
}