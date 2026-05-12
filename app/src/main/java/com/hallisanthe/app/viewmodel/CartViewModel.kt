package com.hallisanthe.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.models.CartItem
import com.hallisanthe.app.models.Order
import com.hallisanthe.app.models.OrderItem
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.repository.CartRepository
import com.hallisanthe.app.repository.OrderRepository
import com.hallisanthe.app.room.DatabaseProvider
import com.hallisanthe.app.utils.UpiPaymentManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

data class CartSummary(
    val itemsSubtotal: Double = 0.0,
    val taxTotal: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val platformFee: Double = 0.0,
    val discount: Double = 0.0,
    val finalTotal: Double = 0.0,
    val sellerEarnings: Double = 0.0
)

sealed class PaymentState {
    object Idle : PaymentState()
    object Processing : PaymentState()
    data class Success(val orderId: String) : PaymentState()
    data class Error(val message: String) : PaymentState()
}

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val cartDao = DatabaseProvider.getDatabase(application).cartDao()
    private val repository = CartRepository(cartDao)
    private val orderRepository = OrderRepository()

    val cartItems: StateFlow<List<CartItem>> = repository.allCartItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _deliveryType = MutableStateFlow("LOCAL_DELIVERY")
    val deliveryType: StateFlow<String> = _deliveryType

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Idle)
    val paymentState: StateFlow<PaymentState> = _paymentState

    private val _lastPaymentMethod = MutableStateFlow("UPI")
    val lastPaymentMethod: StateFlow<String> = _lastPaymentMethod

    val cartSummary: StateFlow<CartSummary> = combine(cartItems, _deliveryType) { items, dType ->
        calculateSummary(items, dType)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartSummary()
    )

    fun setDeliveryType(type: String) {
        _deliveryType.value = type
    }

    private fun calculateSummary(items: List<CartItem>, dType: String): CartSummary {
        var subtotal = 0.0
        var taxTotal = 0.0

        for (item in items) {
            val itemTotal = item.price * item.quantity
            subtotal += itemTotal

            val taxRate = when (item.category.lowercase()) {
                "vegetables", "fruits", "organic" -> 0.00
                "packaged foods", "snacks", "pickles" -> 0.05
                "handicrafts" -> 0.12
                else -> 0.05
            }
            taxTotal += itemTotal * taxRate
        }

        val deliveryFee = if (items.isEmpty() || dType == "SELF_PICKUP") 0.0 else 30.0
        val platformFee = if (items.isEmpty()) 0.0 else 3.0 // Flat platform fee for buyer
        val discount = 0.0 // Placeholder for coupons

        // CORRECT FORMULA: finalTotal = itemTotal + deliveryFee + platformFee + gst - discount
        val finalTotal = subtotal + deliveryFee + platformFee + taxTotal - discount

        // Platform also takes a 5% commission from the seller's portion of the subtotal
        val platformCommissionFromSeller = subtotal * 0.05
        val sellerEarnings = subtotal - platformCommissionFromSeller

        return CartSummary(
            itemsSubtotal = subtotal,
            taxTotal = taxTotal,
            deliveryFee = deliveryFee,
            platformFee = platformFee,
            discount = discount,
            finalTotal = finalTotal,
            sellerEarnings = sellerEarnings
        )
    }

    fun checkoutAndCreateOrder(
        paymentMethod: String,
        transactionId: String = "",
        paymentStatus: String = "PENDING"
    ) {
        _lastPaymentMethod.value = paymentMethod
        viewModelScope.launch {
            _paymentState.value = PaymentState.Processing
            try {
                val items = cartItems.value
                if (items.isEmpty()) {
                    _paymentState.value = PaymentState.Error("Cart is empty")
                    return@launch
                }

                val buyerId = FirebaseManager.auth.currentUser?.uid ?: ""
                val firstSellerId = items.firstOrNull()?.sellerId ?: "test_seller"
                
                val orderId = "HS-${UUID.randomUUID().toString().take(8).uppercase()}"
                val orderItems = items.map {
                    OrderItem(
                        productId = it.productId,
                        name = it.name,
                        price = it.price,
                        quantity = it.quantity,
                        imageUrl = it.imageUrl,
                        category = it.category
                    )
                }

                val summary = cartSummary.value
                val newOrder = Order(
                    orderId = orderId,
                    buyerId = buyerId,
                    sellerId = firstSellerId,
                    items = orderItems,
                    subtotal = summary.itemsSubtotal,
                    gst = summary.taxTotal,
                    deliveryFee = summary.deliveryFee,
                    platformFee = summary.platformFee, // Fee charged to buyer
                    platformCommission = summary.itemsSubtotal * 0.05, // Deducted from seller
                    sellerEarnings = summary.sellerEarnings,
                    totalAmount = summary.finalTotal,
                    finalAmount = summary.finalTotal,
                    paymentMethod = paymentMethod,
                    paymentStatus = paymentStatus,
                    transactionId = transactionId,
                    orderStatus = "PENDING",
                    deliveryType = _deliveryType.value,
                    createdAt = com.google.firebase.Timestamp.now()
                )

                orderRepository.createOrder(newOrder)
                repository.clearCart()
                _paymentState.value = PaymentState.Success(orderId)
            } catch (e: Exception) {
                _paymentState.value = PaymentState.Error(e.message ?: "Failed to create order")
            }
        }
    }

    fun resetPaymentState() {
        _paymentState.value = PaymentState.Idle
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            val cartItem = CartItem(
                productId = product.id,
                name = product.name,
                price = product.price,
                imageUrl = product.imageUrl,
                quantity = quantity,
                category = product.category,
                unit = product.unit,
                sellerId = product.sellerId
            )
            repository.addOrUpdateItem(cartItem)
        }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            repository.updateQuantity(productId, quantity)
        }
    }

    fun removeItem(productId: String) {
        viewModelScope.launch {
            repository.removeItem(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}

