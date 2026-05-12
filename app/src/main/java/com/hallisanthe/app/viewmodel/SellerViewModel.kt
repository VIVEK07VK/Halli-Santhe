package com.hallisanthe.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.models.Inquiry
import com.hallisanthe.app.models.Order
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.repository.InquiryRepository
import com.hallisanthe.app.repository.OrderRepository
import com.hallisanthe.app.repository.ProductRepository
import com.hallisanthe.app.room.DatabaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SellerViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao        = DatabaseProvider.getDatabase(application).productDao()
    private val productRepository = ProductRepository(productDao)
    private val orderRepository   = OrderRepository()
    private val inquiryRepository = InquiryRepository()

    private val currentUserId: String
        get() = FirebaseManager.auth.currentUser?.uid ?: ""

    val sellerProducts: StateFlow<List<Product>> = productRepository.localProducts
        .map { products -> products.filter { it.sellerId == currentUserId } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _totalRevenue = MutableStateFlow(0.0)
    val totalRevenue: StateFlow<Double> = _totalRevenue

    val sellerOrders: StateFlow<List<Order>> = orderRepository.getOrdersForSeller(currentUserId).stateIn(
        scope   = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val sellerInquiries: StateFlow<List<Inquiry>> = inquiryRepository.getInquiriesForSeller(currentUserId).stateIn(
        scope   = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        // Initial fetch
        viewModelScope.launch {
            productRepository.fetchProductsFromFirebase()
        }
        
        viewModelScope.launch {
            sellerOrders.collect { orders ->
                // Revenue is based on sellerEarnings (total minus platform commission)
                _totalRevenue.value = orders
                    .filter { it.orderStatus == "DELIVERED" }
                    .sumOf { it.sellerEarnings }
            }
        }
    }

    /** Add a new product. unit param added. */
    fun addProduct(name: String, price: Double, stock: Int, category: String, imageUrl: String, unit: String = "kg") {
        viewModelScope.launch {
            val newProduct = Product(
                sellerId   = currentUserId,
                name       = name,
                price      = price,
                stock      = stock,
                category   = category,
                imageUrl   = imageUrl,
                unit       = unit,
                sellerName = FirebaseManager.auth.currentUser?.displayName ?: "Seller"
            )
            productRepository.addProduct(newProduct)
        }
    }

    /** Update full product (e.g. from edit dialog). */
    fun updateProduct(product: Product) {
        viewModelScope.launch {
            productRepository.updateProduct(product)
        }
    }

    /** Update just the stock quantity. */
    fun updateProductStock(product: Product, newStock: Int) {
        viewModelScope.launch {
            productRepository.updateProduct(product.copy(stock = newStock))
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            productRepository.deleteProduct(productId)
        }
    }

    fun updateOrderStatus(orderId: String, newStatus: String) {
        viewModelScope.launch {
            orderRepository.updateOrderStatus(orderId, newStatus)
        }
    }

    fun respondToInquiry(inquiryId: String, status: String) {
        viewModelScope.launch {
            inquiryRepository.respondToInquiry(inquiryId, status)
        }
    }
}
