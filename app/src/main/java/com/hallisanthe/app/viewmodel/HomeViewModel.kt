package com.hallisanthe.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.models.Category
import com.hallisanthe.app.models.Product
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao = com.hallisanthe.app.room.DatabaseProvider.getDatabase(application).productDao()
    private val productRepository = com.hallisanthe.app.repository.ProductRepository(productDao)

    private val _selectedCategory = MutableStateFlow<String>("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _recentlyViewed = MutableStateFlow<List<Product>>(emptyList())
    val recentlyViewed: StateFlow<List<Product>> = _recentlyViewed.asStateFlow()

    // Using Flow from Room for real-time updates across the app
    val allProducts: StateFlow<List<Product>> = productRepository.localProducts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val recommendedProducts: StateFlow<List<Product>> = combine(allProducts, _selectedCategory) { products, category ->
        if (category == "All") products.shuffled().take(15)
        else products.filter { it.category.equals(category, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bestSellers: StateFlow<List<Product>> = allProducts.map { list -> 
        list.filter { p -> p.rating >= 4.5 }.take(10) 
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val freshArrivals: StateFlow<List<Product>> = allProducts.map { list -> 
        list.reversed().take(10) 
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val trendingProducts: StateFlow<List<Product>> = allProducts.map { list -> 
        list.shuffled().take(10) 
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadCategories()
        refreshData()
        
        // Safety: If after 3 seconds we still have no products, force seed.
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            if (allProducts.value.isEmpty()) {
                android.util.Log.d("HomeViewModel", "Safety Seed Triggered")
                forceSeed()
            }
        }
    }

    private fun loadCategories() {
        _categories.value = listOf(
            Category("0", "All",                "✨"),
            Category("1", "Vegetables",         "🥬"),
            Category("2", "Fruits",             "🍎"),
            Category("3", "Seeds",              "🌱"),
            Category("4", "Handicrafts",        "🏺"),
            Category("5", "Snacks",             "🍘"),
            Category("6", "Beverages",          "☕"),
            Category("7", "Pickles",            "🥫"),
            Category("8", "Organic Products",    "🍀"),
            Category("9", "Village Specials",   "🏘️"),
            Category("10", "Traditional Foods", "🍛")
        )
    }

    fun refreshData() {
        viewModelScope.launch {
            try {
                android.util.Log.d("HomeViewModel", "Refreshing data from Firebase...")
                productRepository.fetchProductsFromFirebase()
                
                // Wait for the Flow to emit the latest data from Room
                kotlinx.coroutines.delay(1500)
                
                val currentProducts = allProducts.value
                android.util.Log.d("HomeViewModel", "Current products count: ${currentProducts.size}")
                
                if (currentProducts.isEmpty()) {
                    android.util.Log.d("HomeViewModel", "Database empty after refresh. Seeding locally...")
                    forceSeed()
                }
            } catch (e: Exception) {
                android.util.Log.e("HomeViewModel", "Startup sync failed", e)
                // Fallback: Check local again
                if (allProducts.value.isEmpty()) {
                    android.util.Log.d("HomeViewModel", "Database empty after failure. Seeding locally...")
                    forceSeed()
                }
            }
        }
    }

    fun forceSeed() {
        viewModelScope.launch {
            val seedList = com.hallisanthe.app.data.FakeProductData.products
            android.util.Log.d("HomeViewModel", "Seeding ${seedList.size} products to local database...")
            
            // Step 1: Immediate Local Insert
            productDao.insertProducts(seedList)
            
            // Step 2: Background Firestore Sync (Non-blocking)
            launch {
                android.util.Log.d("HomeViewModel", "Starting background sync to Firestore...")
                seedList.forEach { product ->
                    try {
                        productRepository.addProduct(product)
                    } catch (e: Exception) {
                        // Silent fail for individual syncs to avoid breaking the seed
                    }
                }
                android.util.Log.d("HomeViewModel", "Background Firestore sync attempted for all seeded products.")
            }
            
            android.util.Log.d("HomeViewModel", "Local seeding completed.")
        }
    }


    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun getProductById(productId: String): Product? =
        allProducts.value.find { it.id == productId }

    fun addToRecentlyViewed(product: Product) {
        val current = _recentlyViewed.value.toMutableList()
        current.removeAll { it.id == product.id }
        current.add(0, product)
        _recentlyViewed.value = current.take(10)
    }
}
