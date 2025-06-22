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
            is CartIntent.AddToCart -> addToCart(intent.product)
            is CartIntent.RemoveFromCart -> removeFromCart(intent.product)
            is CartIntent.ChangeQuantity -> changeQuantity(intent.product, intent.quantity)
        }
    }

    private fun addToCart(product: Product) {
        val currentItems = _state.value.items.toMutableList()
        val index = currentItems.indexOfFirst { it.product.id == product.id }

        if (index >= 0) {
            val oldItem = currentItems[index]
            val newItem = oldItem.copy(quantity = oldItem.quantity + 1)
            currentItems[index] = newItem
        } else {
            currentItems.add(CartItem(product = product))
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
            if (quantity <= 0) {
                currentItems.removeAt(index)
            } else {
                val oldItem = currentItems[index]
                val newItem = oldItem.copy(quantity = quantity)
                currentItems[index] = newItem
            }
            _state.value = _state.value.copy(items = currentItems)
        }
    }
}
