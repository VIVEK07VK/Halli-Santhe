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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class SellerViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao        = DatabaseProvider.getDatabase(application).productDao()
    private val productRepository = ProductRepository(productDao)
    private val orderRepository   = OrderRepository()
    private val inquiryRepository = InquiryRepository()

    private val _currentUserId = MutableStateFlow(FirebaseManager.auth.currentUser?.uid ?: "")
    val currentUserId: StateFlow<String> = _currentUserId

    val sellerProducts: StateFlow<List<Product>> = kotlinx.coroutines.flow.combine(
        productRepository.localProducts,
        _currentUserId
    ) { products, userId ->
        products.filter { it.sellerId == userId }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _totalRevenue = MutableStateFlow(0.0)
    val totalRevenue: StateFlow<Double> = _totalRevenue

    val sellerOrders: StateFlow<List<Order>> = _currentUserId.flatMapLatest { userId ->
        orderRepository.getOrdersForSeller(userId)
    }.stateIn(
        scope   = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val sellerInquiries: StateFlow<List<Inquiry>> = _currentUserId.flatMapLatest { userId ->
        inquiryRepository.getInquiriesForSeller(userId)
    }.stateIn(
        scope   = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        // Refresh the user ID in case of late initialization
        _currentUserId.value = FirebaseManager.auth.currentUser?.uid ?: ""
        
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
                sellerId   = currentUserId.value,
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

    fun refreshProducts() {
        viewModelScope.launch {
            productRepository.fetchProductsFromFirebase()
        }
    }
}
