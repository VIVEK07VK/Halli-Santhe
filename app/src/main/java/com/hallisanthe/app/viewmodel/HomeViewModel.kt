package com.hallisanthe.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hallisanthe.app.models.Category
import com.hallisanthe.app.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _allRecommendedProducts = MutableStateFlow<List<Product>>(emptyList())
    
    private val _selectedCategory = MutableStateFlow<String>("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _recommendedProducts = MutableStateFlow<List<Product>>(emptyList())
    val recommendedProducts: StateFlow<List<Product>> = _recommendedProducts.asStateFlow()

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        filterRecommendedProducts()
    }

    private fun filterRecommendedProducts() {
        val category = _selectedCategory.value
        val all = _allRecommendedProducts.value
        _recommendedProducts.value = if (category == "All") {
            all
        } else {
            all.filter { it.category.equals(category, ignoreCase = true) }
        }
    }

    private val _bestSellers = MutableStateFlow<List<Product>>(emptyList())
    val bestSellers: StateFlow<List<Product>> = _bestSellers.asStateFlow()

    private val _freshArrivals = MutableStateFlow<List<Product>>(emptyList())
    val freshArrivals: StateFlow<List<Product>> = _freshArrivals.asStateFlow()

    private val _trendingProducts = MutableStateFlow<List<Product>>(emptyList())
    val trendingProducts: StateFlow<List<Product>> = _trendingProducts.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _recentlyViewed = MutableStateFlow<List<Product>>(emptyList())
    val recentlyViewed: StateFlow<List<Product>> = _recentlyViewed.asStateFlow()

    // Full catalogue — used by Search
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts.asStateFlow()

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        val recommended = listOf(
            Product(id = "1",  name = "Guava (Peru)",      price = 60.0,  unit = "kg",   category = "Fruits",       imageUrl = "https://images.unsplash.com/photo-1536511132770-e5058c7e8c46?w=400&q=80", rating = 4.4, discountPercent = 17, tag = "VILLAGE SPECIAL", sellerName = "Ramu Farms", description = "Fresh guavas sourced from local farms"),
            Product(id = "2",  name = "Custard Apple",     price = 80.0,  unit = "kg",   category = "Fruits",       imageUrl = "https://images.unsplash.com/photo-1596484552834-6a58f84bfc88?w=400&q=80", rating = 4.9, discountPercent = 17, tag = "VILLAGE SPECIAL", sellerName = "Kavitha Devi", description = "Sweet & creamy custard apples"),
            Product(id = "7",  name = "Green Beans",       price = 40.0,  unit = "kg",   category = "Vegetables",   imageUrl = "https://images.unsplash.com/photo-1590005354167-6da9782046e5?w=400&q=80", rating = 4.5, sellerName = "Suresh Veggies", description = "Farm-fresh green beans"),
            Product(id = "19", name = "Bamboo Basket",     price = 350.0, unit = "pc",   category = "Handicrafts",  imageUrl = "https://images.unsplash.com/photo-1606760227091-3dd870d97f1d?w=400&q=80", rating = 4.8, tag = "HANDMADE", sellerName = "Artisan Crafts", description = "Hand-woven bamboo baskets"),
            Product(id = "3",  name = "Honey",             price = 250.0, unit = "jar",  category = "Organic Products", imageUrl = "https://images.unsplash.com/photo-1587049352847-4d4b1ed7d853?w=400&q=80", rating = 4.7, sellerName = "Namma Honey Farm", description = "Pure wild forest honey")
        )

        val bestSellers = listOf(
            Product(id = "3",  name = "Honey",             price = 250.0, unit = "jar",  category = "Organic",      imageUrl = "https://images.unsplash.com/photo-1587049352847-4d4b1ed7d853?w=400&q=80", sellerName = "Namma Honey Farm", description = "Pure wild forest honey"),
            Product(id = "4",  name = "Millets",           price = 70.0,  unit = "kg",   category = "Groceries",    imageUrl = "https://images.unsplash.com/photo-1586201375761-83865001e8ac?w=400&q=80", sellerName = "Organic Village", description = "Nutritious multi-grain millets"),
            Product(id = "5",  name = "Groundnut Oil",     price = 180.0, unit = "L",    category = "Groceries",    imageUrl = "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=400&q=80", sellerName = "Cold Press Oil Co.", description = "Cold-pressed pure groundnut oil"),
            Product(id = "6",  name = "Turmeric Powder",   price = 120.0, unit = "kg",   category = "Groceries",    imageUrl = "https://images.unsplash.com/photo-1615485925600-97237c4fc1ec?w=400&q=80", sellerName = "Spice Hut", description = "Pure turmeric from local farms")
        )

        val freshArrivals = listOf(
            Product(id = "7",  name = "Green Beans",       price = 40.0,  unit = "kg",   category = "Vegetables",   imageUrl = "https://images.unsplash.com/photo-1590005354167-6da9782046e5?w=400&q=80", sellerName = "Suresh Veggies", description = "Farm-fresh green beans"),
            Product(id = "8",  name = "Drumstick",         price = 50.0,  unit = "kg",   category = "Vegetables",   imageUrl = "https://images.unsplash.com/photo-1616421946804-03f9059ea9d2?w=400&q=80", sellerName = "Village Green", description = "Organic drumstick from kitchen gardens"),
            Product(id = "9",  name = "Red Banana",        price = 60.0,  unit = "kg",   category = "Fruits",       imageUrl = "https://images.unsplash.com/photo-1571501679680-a9b050c377f8?w=400&q=80", sellerName = "Fruit Garden", description = "Naturally ripened red bananas"),
            Product(id = "10", name = "Coconut",           price = 35.0,  unit = "pc",   category = "Fruits",       imageUrl = "https://images.unsplash.com/photo-1596547609652-9cb5d8d172e8?w=400&q=80", sellerName = "Coastal Farms", description = "Fresh mature coconuts")
        )

        val trendingProducts = listOf(
            Product(id = "11", name = "Jaggery",           price = 55.0,  unit = "kg",   category = "Groceries",    imageUrl = "https://plus.unsplash.com/premium_photo-1671436821213-3b1a806c9a9d?w=400&q=80", sellerName = "Traditional Sweets", description = "Organic cane jaggery"),
            Product(id = "12", name = "Pickle (Mix)",      price = 120.0, unit = "jar",  category = "Pickles",      imageUrl = "https://images.unsplash.com/photo-1528750841285-d72b834e0078?w=400&q=80", sellerName = "Avva's Kitchen", description = "Traditional mixed pickle – grandma's recipe"),
            Product(id = "13", name = "Ragi Flour",        price = 65.0,  unit = "kg",   category = "Groceries",    imageUrl = "https://images.unsplash.com/photo-1508061461528-ce2ee16d1cd4?w=400&q=80", sellerName = "Millet House", description = "Stone-ground finger millet flour"),
            Product(id = "14", name = "Cold Pressed Oil",  price = 200.0, unit = "L",    category = "Organic",      imageUrl = "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=400&q=80", sellerName = "Cold Press Oil Co.", description = "Sesame cold-pressed oil")
        )

        val recentlyViewed = listOf(
            Product(id = "15", name = "Tomato",            price = 30.0,  unit = "kg",   category = "Vegetables",   imageUrl = "https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=400&q=80", sellerName = "Farm Direct", description = "Vine-ripened tomatoes"),
            Product(id = "16", name = "Onion",             price = 25.0,  unit = "kg",   category = "Vegetables",   imageUrl = "https://images.unsplash.com/photo-1618512496248-a07ce83aa8cb?w=400&q=80", sellerName = "Village Farms", description = "Red onions from Karnataka"),
            Product(id = "17", name = "Brinjal",           price = 35.0,  unit = "kg",   category = "Vegetables",   imageUrl = "https://images.unsplash.com/photo-1601493700631-2b16ec4b4716?w=400&q=80", sellerName = "Village Farms", description = "Purple brinjal – fresh daily"),
            Product(id = "18", name = "Curry Leaves",      price = 10.0,  unit = "bunch", category = "Vegetables",  imageUrl = "https://images.unsplash.com/photo-1634567404481-424a73229b47?w=400&q=80", sellerName = "Kitchen Garden", description = "Fresh curry leaves"),
            Product(id = "19", name = "Bamboo Basket",     price = 350.0, unit = "pc",   category = "Handicrafts",  imageUrl = "https://images.unsplash.com/photo-1606760227091-3dd870d97f1d?w=400&q=80", sellerName = "Artisan Crafts", description = "Hand-woven bamboo baskets"),
            Product(id = "20", name = "Chilli Powder",     price = 90.0,  unit = "kg",   category = "Groceries",    imageUrl = "https://images.unsplash.com/photo-1584947897558-4e41a8b3b32a?w=400&q=80", sellerName = "Spice Hut", description = "Pure Byadagi chilli powder")
        )

        _allRecommendedProducts.value = recommended
        _recommendedProducts.value = recommended
        _bestSellers.value = bestSellers
        _freshArrivals.value = freshArrivals
        _trendingProducts.value = trendingProducts
        _recentlyViewed.value = recentlyViewed
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

        // All products combined
        _allProducts.value = (recommended + bestSellers + freshArrivals + trendingProducts + recentlyViewed)
            .distinctBy { it.id }
    }

    fun getProductById(productId: String): Product? =
        _allProducts.value.find { it.id == productId }

    fun addToRecentlyViewed(product: Product) {
        val current = _recentlyViewed.value.toMutableList()
        current.removeAll { it.id == product.id }
        current.add(0, product)
        _recentlyViewed.value = current.take(10)
    }
}
