package com.example.myapplication.ui.cart
import com.example.myapplication.data.Entities.CartItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Api.CartApi
import com.example.myapplication.data.Entities.CartRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.myapplication.data.Entities.Product
import com.example.myapplication.data.Session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlin.collections.filter
@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartApi: CartApi,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(CartViewState())
    val state: StateFlow<CartViewState> = _state

    fun onIntent(intent: CartIntent) {
        when (intent) {
            is CartIntent.AddToCart -> addToCart(intent.product, intent.selectedSize)
            is CartIntent.RemoveFromCart -> removeFromCart(intent.product)
            is CartIntent.ChangeQuantity -> changeQuantity(intent.product, intent.quantity)
        }
    }

    private fun addToCart(product: Product, selectedSize: String) {
        val currentItems = _state.value.items.toMutableList()
        val availableQuantity = product.sizes.firstOrNull { it.size == selectedSize }?.quantity ?: 0

        if (availableQuantity <= 0) return

        val index = currentItems.indexOfFirst {
            it.product.id == product.id && it.selectedSize == selectedSize
        }

        if (index >= 0) {
            val oldItem = currentItems[index]
            if (oldItem.quantity < availableQuantity) {
                currentItems[index] = oldItem.copy(quantity = oldItem.quantity + 1)
            }
        } else {
            currentItems.add(CartItem(product = product, selectedSize = selectedSize))
        }

        _state.value = _state.value.copy(items = currentItems)
        sessionManager.getUser()?.let { saveCartForUser(it.id) }
    }

    fun removeFromCart(product: Product) {
        val currentItems = _state.value.items.filter { it.product.id != product.id }
        _state.value = _state.value.copy(items = currentItems)
        sessionManager.getUser()?.let { saveCartForUser(it.id) }
    }

    fun removeOneItem(cartItem: CartItem) {
        val currentItems = _state.value.items.toMutableList()
        val index = currentItems.indexOfFirst {
            it.product.id == cartItem.product.id && it.selectedSize == cartItem.selectedSize
        }

        if (index != -1) {
            val item = currentItems[index]
            if (item.quantity > 1) {
                currentItems[index] = item.copy(quantity = item.quantity - 1)
            } else {
                currentItems.removeAt(index)
            }
            _state.value = _state.value.copy(items = currentItems)
            sessionManager.getUser()?.let { saveCartForUser(it.id) }
        }
    }

    private fun changeQuantity(product: Product, quantity: Int) {
        val currentItems = _state.value.items.toMutableList()
        val index = currentItems.indexOfFirst { it.product.id == product.id }

        if (index >= 0) {
            val item = currentItems[index]
            val availableQuantity = item.product.sizes
                .firstOrNull { it.size == item.selectedSize }?.quantity ?: 0

            when {
                quantity <= 0 -> currentItems.removeAt(index)
                quantity <= availableQuantity -> {
                    currentItems[index] = item.copy(quantity = quantity)
                }
            }

            _state.value = _state.value.copy(items = currentItems)
            sessionManager.getUser()?.let { saveCartForUser(it.id) }
        }
    }

    fun clearCart() {
        _state.value = CartViewState(emptyList())
        sessionManager.getUser()?.let { saveCartForUser(it.id) }
    }

    fun resetAddToCartState() {
        _state.value = _state.value.copy(
            // Ajoute ici des flags d’état si tu en utilises
        )
    }

    fun loadCartForUser(userId: String, allProducts: List<Product>) {
        viewModelScope.launch {
            try {
                val items = cartApi.getCart(userId)

                val productItems = items.mapNotNull { cartItem ->
                    val product = allProducts.find { it.id == cartItem.productId }
                    product?.let {
                        CartItem(it, cartItem.quantity, cartItem.selectedSize)
                    }
                }

                _state.value = CartViewState(productItems)
            } catch (e: Exception) {
                // Log.e("Cart", "Erreur chargement panier : ${e.message}")
            }
        }
    }

    fun clearCartForUser(userId: String) {
        viewModelScope.launch {
            try {
                cartApi.clearCart(userId) // ← si tu utilises @DELETE("/cart/{userId}")
                _state.value = CartViewState(emptyList()) // vide localement aussi
            } catch (e: Exception) {
                println("Erreur lors du vidage du panier côté backend : ${e.message}")
            }
        }
    }


    fun saveCartForUser(userId: String) {
        viewModelScope.launch {
            try {
                val payload = _state.value.items.map {
                    CartRequest.Item(
                        productId = it.product.id,
                        quantity = it.quantity,
                        selectedSize = it.selectedSize
                    )
                }
            } catch (e: Exception) {
                // Log.e("Cart", "Erreur sauvegarde panier : ${e.message}")
            }
        }
    }
}