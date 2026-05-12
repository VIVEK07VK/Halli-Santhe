package com.hallisanthe.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.repository.EarningsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SellerEarningsSummary(
    val totalSales: Double = 0.0,
    val totalRevenue: Double = 0.0,
    val todayEarnings: Double = 0.0,
    val totalCommissionDeducted: Double = 0.0,
    val completedOrdersCount: Int = 0
)

class EarningsViewModel : ViewModel() {
    private val repository = EarningsRepository()
    private val currentUserId: String
        get() = FirebaseManager.auth.currentUser?.uid ?: ""

    private val _earningsSummary = MutableStateFlow(SellerEarningsSummary())
    val earningsSummary: StateFlow<SellerEarningsSummary> = _earningsSummary

    init {
        loadEarnings()
    }

    private fun loadEarnings() {
        if (currentUserId.isEmpty()) return
        
        viewModelScope.launch {
            repository.getCompletedOrdersForSeller(currentUserId).collect { orders ->
                var sales = 0.0
                var revenue = 0.0
                var commission = 0.0
                var today = 0.0
                
                // For simplicity, we assume 'today' logic checking timestamps.
                // We'll approximate today's earnings using a basic filter or all for MVP.
                val currentTime = System.currentTimeMillis()
                val oneDayMs = 24 * 60 * 60 * 1000

                orders.forEach { order ->
                    sales += order.finalAmount
                    revenue += order.sellerEarnings
                    commission += order.platformCommission
                    
                    if (currentTime - order.createdAt.toDate().time < oneDayMs) {
                        today += order.sellerEarnings
                    }
                }

                _earningsSummary.value = SellerEarningsSummary(
                    totalSales = sales,
                    totalRevenue = revenue,
                    todayEarnings = today,
                    totalCommissionDeducted = commission,
                    completedOrdersCount = orders.size
                )
            }
        }
    }
}
