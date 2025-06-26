package com.example.myapplication.ui.order
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
): ViewModel() {

    private val _state = MutableStateFlow<OrderState>(OrderState.Idle)
    val state: StateFlow<OrderState> = _state.asStateFlow()

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
}