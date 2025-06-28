package com.example.myapplication.ui.order
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Entities.Address
import com.example.myapplication.data.Entities.Order
import com.example.myapplication.data.Entities.User
import com.example.myapplication.data.Repository.OrderRepository
import com.example.myapplication.data.Session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val sessionManager: SessionManager
): ViewModel() {
    private val _userOrders = MutableStateFlow<List<Order>>(emptyList())
    val userOrders: StateFlow<List<Order>> = _userOrders.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _state = MutableStateFlow<OrderState>(OrderState.Idle)
    val state: StateFlow<OrderState> = _state.asStateFlow()

    // Nouveau: Stockage temporaire de l'adresse
    private val _shippingAddress = MutableStateFlow<Address?>(null)
    val shippingAddress: StateFlow<Address?> = _shippingAddress.asStateFlow()

    fun setShippingAddress(address: Address) {
        _shippingAddress.value = address
    }

    fun loadOrders(userId: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getOrders(userId)
                _userOrders.value = result
            } catch (e: Exception) {
                _userOrders.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun handleIntent(intent: OrderIntent) {
        when (intent) {
            is OrderIntent.PlaceOrder -> {
                _state.value = OrderState.Loading
                viewModelScope.launch {
                    try {
                        val result = repository.placeOrder(intent.order)
                        _state.value = OrderState.Success(result)
                    } catch (e: Exception) {
                        _state.value = OrderState.Error(e.message ?: "Unknown error")
                    }
                }
            }
        }
    }
    val currentUser: User?
        get() = sessionManager.getUser()

}