package com.example.myapplication.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProductViewState())
    val state: StateFlow<ProductViewState> = _state

    fun handleIntent(intent: ProductIntent) {
        when (intent) {
            is ProductIntent.LoadProducts -> loadProducts()
            is ProductIntent.ShowProductDetails -> {
                // Tu peux gérer ça plus tard si tu veux charger un produit unique
            }
        }
    }

    private fun loadProducts() {
        _state.value = ProductViewState(isLoading = true)
        viewModelScope.launch {
            try {
                val products = repository.getProducts()
                _state.value = ProductViewState(products = products)
            } catch (e: Exception) {
                _state.value = ProductViewState(error = e.message ?: "Erreur inconnue")
            }
        }
    }
}
