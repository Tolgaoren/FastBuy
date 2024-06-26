package com.toren.producthub.data.remote.api

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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts() : ProductsDto

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int) : ProductResponse

    @GET("products/categories")
    suspend fun getCategories() : CategoriesDto

    @GET("products/category/{category}")
    suspend fun getCategoryProducts(@Path("category") category: String) : ProductsDto

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String) : ProductsDto

    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: Int) : UserDto

    @PATCH("user/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: User) : UserDto

    @POST("auth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body request: AuthRequest) : AuthResponse

    @GET("carts/user/{id}")
    suspend fun getCartByUser(@Path ("id") id: Int) : CartsByUserDto

    @POST("carts/add")
    @Headers("Content-Type: application/json")
    suspend fun addCart(@Body addCartRequest: AddCartRequest) : AddCartResponse
}