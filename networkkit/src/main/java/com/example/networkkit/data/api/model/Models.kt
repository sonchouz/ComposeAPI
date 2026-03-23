package com.example.networkkit.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String,
    val user: UserDto,
    val expiresIn: Long? = null
)

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String? = null,
    val phone: String? = null
)

@Serializable
data class RegisterResponse(
    val user: UserDto,
    val token: String? = null,      // иногда сразу дают токен
    val message: String? = null
)
@Serializable
data class UpdateProfileRequest(
    val name: String? = null,
    val phone: String? = null,
    val avatarUrl: String? = null,
    // любые другие поля профиля
)

@Serializable
data class ProfileResponse(
    val id: String,
    val email: String,
    val name: String?,
    val phone: String?,
    val avatarUrl: String?,
    val createdAt: String?,
    val updatedAt: String?
)
@Serializable
data class PromotionDto(
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val discountPercent: Int?,
    val startDate: String?,
    val endDate: String?,
    val isActive: Boolean
)

@Serializable
data class PromotionsResponse(
    val promotions: List<PromotionDto>,
    val total: Int? = null
)
@Serializable
data class CategoryDto(
    val id: String,
    val name: String,
    val imageUrl: String? = null
)

@Serializable
data class ProductShortDto(
    val id: String,
    val name: String,
    val price: Double,
    val oldPrice: Double? = null,
    val imageUrl: String?,
    val rating: Float? = null,
    val reviewsCount: Int? = null,
    val isFavorite: Boolean = false
)

@Serializable
data class CatalogResponse(
    val categories: List<CategoryDto>? = null,
val products: List<ProductShortDto>,
val totalPages: Int? = null,
val currentPage: Int? = null
)
@Serializable
data class SearchResponse(
    val query: String,
    val products: List<ProductShortDto>,
    val total: Int
)

@Serializable
data class ProductDetailResponse(
    val id: String,
    val name: String,
    val description: String?,
    val price: Double,
    val oldPrice: Double? = null,
    val images: List<String>,
    val rating: Float? = null,
    val reviewsCount: Int? = null,
    val specifications: Map<String, String>? = null,  // характеристики
    val isInCart: Boolean = false,
    val isFavorite: Boolean = false
)
@Serializable
data class CartItemDto(
    val id: String,             // id позиции в корзине
    val productId: String,
    val productName: String,
    val productImage: String?,
    val price: Double,
    val quantity: Int,
    val total: Double
)

@Serializable
data class AddToCartRequest(
    val productId: String,
    val quantity: Int = 1
)

@Serializable
data class UpdateCartRequest(
    val quantity: Int
)

@Serializable
data class CartResponse(
    val items: List<CartItemDto>,
    val totalItems: Int,
    val totalPrice: Double,
    val deliveryPrice: Double? = null
)
@Serializable
data class CreateOrderRequest(
    val address: String,
    val comment: String? = null,
    val paymentMethod: String,      // "card", "cash", "online"
    val deliveryMethod: String?     // "courier", "pickup"
)

@Serializable
data class OrderResponse(
    val orderId: String,
    val status: String,
    val total: Double,
    val createdAt: String,
    val message: String? = null
)

@Serializable
data class ProjectDto(
    val id: String,
    val name: String,
    val description: String?,
    val status: String,             // "active", "completed", "draft"
    val createdAt: String,
    val itemsCount: Int? = null
)

@Serializable
data class ProjectsResponse(
    val projects: List<ProjectDto>,
    val total: Int
)

@Serializable
data class CreateProjectRequest(
    val name: String,
    val description: String? = null
)

@Serializable
data class ProjectResponse(
    val project: ProjectDto,
    val message: String? = null
)
@Serializable
data class LogoutResponse(
    val message: String,
    val success: Boolean
)
@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val name: String?,
    val phone: String?,
    val avatarUrl: String?,
    val role: String? = null,      // "user", "admin" и т.д.
    val createdAt: String?
)