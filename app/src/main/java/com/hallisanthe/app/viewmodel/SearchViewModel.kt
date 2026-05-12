package com.hallisanthe.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.repository.SearchRepository
import com.hallisanthe.app.room.DatabaseProvider
import com.hallisanthe.app.room.RecentSearchEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SearchRepository(DatabaseProvider.getDatabase(application).recentSearchDao())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    
    // Filtered products based on query
    val filteredProducts: StateFlow<List<Product>> = combine(_searchQuery, _allProducts) { query, products ->
        if (query.isBlank()) {
            emptyList()
        } else {
            products.filter { product ->
                product.name.contains(query, ignoreCase = true) ||
                product.sellerName.contains(query, ignoreCase = true) ||
                product.category.contains(query, ignoreCase = true) ||
                product.description.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val recentSearches: StateFlow<List<RecentSearchEntity>> = repository.getRecentSearches()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setAllProducts(products: List<Product>) {
        _allProducts.value = products
    }

    fun onSearchExecuted(query: String) {
        viewModelScope.launch {
            repository.saveSearch(query)
        }
    }

    fun deleteRecentSearch(query: String) {
        viewModelScope.launch {
            repository.deleteSearch(query)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
