// data/api/ApiService.kt
package com.example.networkkit.data.api

import com.example.networkkit.data.api.model.CreateOrderRequest
import com.example.networkkit.data.api.model.CreateProjectRequest
import com.example.networkkit.data.api.model.LogoutResponse
import com.example.networkkit.data.api.model.OrderResponse
import com.example.networkkit.data.api.model.ProfileResponse
import com.example.networkkit.data.api.model.ProjectDto
import com.example.networkkit.data.api.model.UpdateCartRequest
import com.example.networkkit.data.api.model.*
import retrofit2.http.*

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @PUT("profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): ProfileResponse

    @GET("promotions")
    suspend fun getPromotions(): List<PromotionDto>

    @GET("catalog")
    suspend fun getCatalog(@Query("page") page: Int = 1, @Query("limit") limit: Int = 20): CatalogResponse

    @GET("search")
    suspend fun search(@Query("q") query: String, @Query("page") page: Int = 1): SearchResponse

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: String): ProductDetailResponse

    @POST("cart/items")
    suspend fun addToCart(@Body request: AddToCartRequest): CartResponse

    @PUT("cart/items/{id}")
    suspend fun updateCartItem(@Path("id") id: String, @Body request: UpdateCartRequest): CartResponse

    @POST("orders")
    suspend fun createOrder(@Body request: CreateOrderRequest): OrderResponse

    @GET("projects")
    suspend fun getProjects(): List<ProjectDto>

    @POST("projects")
    suspend fun createProject(@Body request: CreateProjectRequest): ProjectDto

    @GET("profile")
    suspend fun getProfile(): ProfileResponse

    @POST("auth/logout")
    suspend fun logout(): LogoutResponse
}
