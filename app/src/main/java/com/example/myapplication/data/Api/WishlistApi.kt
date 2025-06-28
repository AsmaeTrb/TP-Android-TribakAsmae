package com.example.myapplication.data.Api

import com.example.myapplication.data.Entities.WishlistItem
import com.example.myapplication.data.Entities.AddWishlistRequest
import retrofit2.Response
import retrofit2.http.*

interface WishlistApi {
    @GET("wishlist/{userId}")
    suspend fun getWishlist(@Path("userId") userId: String): List<WishlistItem>

    @POST("wishlist")
    suspend fun addToWishlist(@Body request: AddWishlistRequest): Response<Unit>

    @DELETE("wishlist/{userId}/{productId}")
    suspend fun removeFromWishlist(
        @Path("userId") userId: String,
        @Path("productId") productId: String
    ): Response<Unit>
}
