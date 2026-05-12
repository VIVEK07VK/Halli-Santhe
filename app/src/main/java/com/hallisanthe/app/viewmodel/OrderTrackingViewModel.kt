package com.hallisanthe.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.models.Order
import com.hallisanthe.app.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderTrackingViewModel : ViewModel() {
    private val orderRepository = OrderRepository()

    private val _currentOrder = MutableStateFlow<Order?>(null)
    val currentOrder: StateFlow<Order?> = _currentOrder

    fun trackOrder(orderId: String) {
        viewModelScope.launch {
            orderRepository.getOrderTracking(orderId).collect { order ->
                _currentOrder.value = order
            }
        }
    }
}
