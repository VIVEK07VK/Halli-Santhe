package com.hallisanthe.app.repository

import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.room.ProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ProductRepository(private val productDao: ProductDao) {
    
    val localProducts: Flow<List<Product>> = productDao.getAllProducts()

    suspend fun fetchProductsFromFirebase() {
        try {
            val snapshot = FirebaseManager.firestore.collection("products").get().await()
            val products = snapshot.toObjects(Product::class.java)
            productDao.insertProducts(products)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getProductsBySellerId(sellerId: String): List<Product> {
        return try {
            val snapshot = FirebaseManager.firestore.collection("products")
                .whereEqualTo("sellerId", sellerId)
                .get().await()
            snapshot.toObjects(Product::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addProduct(product: Product) {
        try {
            val finalId = if (product.id.isNotEmpty()) product.id else UUID.randomUUID().toString()
            val finalProduct = if (product.id.isNotEmpty()) product else product.copy(id = finalId)
            
            FirebaseManager.firestore.collection("products")
                .document(finalId)
                .set(finalProduct)
                .await()
            
            productDao.insertProducts(listOf(finalProduct))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateProduct(product: Product) {
        try {
            FirebaseManager.firestore.collection("products").document(product.id).set(product).await()
            productDao.insertProducts(listOf(product))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteProduct(productId: String) {
        try {
            FirebaseManager.firestore.collection("products").document(productId).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
