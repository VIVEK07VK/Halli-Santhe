package com.hallisanthe.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.models.FavoriteEntity
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.repository.FavoriteRepository
import com.hallisanthe.app.room.DatabaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FavoriteRepository(
        DatabaseProvider.getDatabase(application).favoriteDao()
    )

    /** Live list of all favorited products (persists across restarts). */
    val favorites: StateFlow<List<FavoriteEntity>> = repository.allFavorites.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    /** Set of productIds that are currently favorited — fast O(1) lookup in UI. */
    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    init {
        // Keep favoriteIds in sync with the Room favorites table
        viewModelScope.launch {
            favorites.collect { list ->
                _favoriteIds.value = list.map { it.productId }.toSet()
            }
        }
    }

    /**
     * Toggle favorite state for [product].
     * Instantly updates [favoriteIds] for responsive UI, then persists to Room.
     */
    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            val newFavStatus = repository.toggleFavorite(product)
            // favoriteIds is kept in sync by the collector above, but we can
            // also update immediately for snappy UI:
            _favoriteIds.value = if (newFavStatus) {
                _favoriteIds.value + product.id
            } else {
                _favoriteIds.value - product.id
            }
        }
    }

    fun isFavorite(productId: String): Boolean = _favoriteIds.value.contains(productId)

    fun removeFavorite(productId: String) {
        viewModelScope.launch {
            repository.removeFavorite(productId)
        }
    }
}
