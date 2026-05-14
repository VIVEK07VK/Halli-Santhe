package com.hallisanthe.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.models.Order
import com.hallisanthe.app.models.OrderStatus
import com.hallisanthe.app.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel : ViewModel() {
    private val repository = OrderRepository()

    private val _sellerOrders = MutableStateFlow<List<Order>>(emptyList())
    val sellerOrders: StateFlow<List<Order>> = _sellerOrders.asStateFlow()

    private val _buyerOrders = MutableStateFlow<List<Order>>(emptyList())
    val buyerOrders: StateFlow<List<Order>> = _buyerOrders.asStateFlow()

    private val _currentTrackingOrder = MutableStateFlow<Order?>(null)
    val currentTrackingOrder: StateFlow<Order?> = _currentTrackingOrder.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun listenToSellerOrders(sellerId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getOrdersForSeller(sellerId).collect { orders ->
                _sellerOrders.value = orders
                _isLoading.value = false
            }
        }
    }

    fun listenToBuyerOrders(buyerId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getOrdersForBuyer(buyerId).collect { orders ->
                _buyerOrders.value = orders
                _isLoading.value = false
            }
        }
    }

    fun startTracking(orderId: String) {
        viewModelScope.launch {
            repository.getOrderTracking(orderId).collect { order ->
                _currentTrackingOrder.value = order
            }
        }
    }

    fun updateStatus(orderId: String, newStatus: OrderStatus) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, newStatus.name)
        }
    }

    fun acceptOrder(orderId: String) {
        updateStatus(orderId, OrderStatus.ACCEPTED)
    }

    fun rejectOrder(orderId: String) {
        updateStatus(orderId, OrderStatus.REJECTED)
    }

    fun markPreparing(orderId: String) {
        updateStatus(orderId, OrderStatus.PREPARING)
    }

    fun markReady(orderId: String) {
        updateStatus(orderId, OrderStatus.READY_FOR_PICKUP)
    }

    fun markOutForDelivery(orderId: String) {
        updateStatus(orderId, OrderStatus.OUT_FOR_DELIVERY)
    }

    fun markDelivered(orderId: String) {
        updateStatus(orderId, OrderStatus.DELIVERED)
    }
}
