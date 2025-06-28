package com.example.myapplication.ui.product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Api.WishlistApi
import kotlinx.coroutines.flow.*
import com.example.myapplication.data.Entities.AddWishlistRequest
import com.example.myapplication.data.Entities.WishlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistApi: WishlistApi
) : ViewModel() {

    private val _wishlist = MutableStateFlow<List<WishlistItem>>(emptyList())
    val wishlist: StateFlow<List<WishlistItem>> = _wishlist.asStateFlow()

    fun loadWishlist(userId: String) {
        viewModelScope.launch {
            try {
                _wishlist.value = wishlistApi.getWishlist(userId)
            } catch (e: Exception) {
                println("Erreur récupération favoris: ${e.message}")
            }
        }
    }

    fun addToWishlist(userId: String, item: WishlistItem) {
        viewModelScope.launch {
            try {
                wishlistApi.addToWishlist(AddWishlistRequest(userId, item))
                loadWishlist(userId)
            } catch (e: Exception) {
                println("Erreur ajout favoris: ${e.message}")
            }
        }
    }

    fun removeFromWishlist(userId: String, productId: String) {
        viewModelScope.launch {
            try {
                wishlistApi.removeFromWishlist(userId, productId)
                loadWishlist(userId)
            } catch (e: Exception) {
                println("Erreur suppression favoris: ${e.message}")
            }
        }
    }

    fun isProductInWishlist(productId: String): StateFlow<Boolean> {
        return wishlist
            .map { list -> list.any { it.productId == productId } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    }
}