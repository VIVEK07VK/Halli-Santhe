package com.hallisanthe.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val products: StateFlow<List<Product>> = repository.localProducts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        refreshProducts()
    }

    private fun refreshProducts() {
        viewModelScope.launch {
            repository.fetchProductsFromFirebase()
        }
    }
}
