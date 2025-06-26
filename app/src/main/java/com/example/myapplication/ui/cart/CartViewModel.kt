package com.example.myapplication.ui.cart
import com.example.myapplication.data.Entities.CartItem
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.myapplication.data.Entities.Product
import kotlin.collections.filter
class CartViewModel : ViewModel() {

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

        if (availableQuantity <= 0) return // Ne pas ajouter si pas de stock

        val index = currentItems.indexOfFirst {
            it.product.id == product.id && it.selectedSize == selectedSize
        }

        if (index >= 0) {
            val oldItem = currentItems[index]
            if (oldItem.quantity < availableQuantity) {
                val newItem = oldItem.copy(quantity = oldItem.quantity + 1)
                currentItems[index] = newItem
            }
        } else {
            currentItems.add(CartItem(product = product, selectedSize = selectedSize))
        }
        _state.value = _state.value.copy(items = currentItems)
    }

    private fun removeFromCart(product: Product) {
        val currentItems = _state.value.items.filter { it.product.id != product.id }
        _state.value = _state.value.copy(items = currentItems)
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
                    val newItem = item.copy(quantity = quantity)
                    currentItems[index] = newItem
                }
                // Sinon, ne pas dépasser la quantité disponible
            }
            _state.value = _state.value.copy(items = currentItems)
        }
    }
    fun resetAddToCartState() {
        _state.value = _state.value.copy(
            // Réinitialisez les états nécessaires ici
        )
    }
    fun clearCart() {
        _state.value = CartViewState(emptyList())
    }
}