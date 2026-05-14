package com.hallisanthe.app.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hallisanthe.app.ui.screens.*
import com.hallisanthe.app.viewmodel.*

fun NavGraphBuilder.sellerGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
    ordersViewModel: OrdersViewModel,
    profileViewModel: ProfileViewModel
) {
    navigation(startDestination = Routes.SELLER_DASHBOARD, route = "seller_graph") {
        
        composable(Routes.SELLER_DASHBOARD) { backStack ->
            val sellerBackStackEntry = remember(backStack) {
                navController.getBackStackEntry("seller_graph")
            }
            val sellerViewModel: SellerViewModel = viewModel(sellerBackStackEntry)

            SellerDashboardScreen(
                sellerViewModel    = sellerViewModel,
                onNavigateToOrders = { navController.navigate(Routes.SELLER_ORDERS) },
                onNavigateToProducts = { navController.navigate(Routes.SELLER_PRODUCTS) },
                onNavigateToProfile = { navController.navigate(Routes.SELLER_PROFILE) },
                onNavigateToAddProduct = { navController.navigate(Routes.SELLER_ADD_PRODUCT) },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.ROLE_SELECTION) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.SELLER_ORDERS) {
            ManageOrdersScreen(
                onBack = { navController.popBackStack() },
                ordersViewModel = ordersViewModel,
                authViewModel = authViewModel
            )
        }

        composable(Routes.SELLER_ADD_PRODUCT) {
            AddProductScreen(
                onBack    = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }

        composable(Routes.SELLER_PROFILE) {
            SellerProfileScreen(
                authViewModel    = authViewModel,
                onBack           = { navController.popBackStack() },
                onNavigate       = { route -> navController.navigate(route) },
                onLogout         = {
                    authViewModel.logout()
                    navController.navigate(Routes.ROLE_SELECTION) { popUpTo(0) { inclusive = true } }
                }
            )
        }

        composable(Routes.EDIT_PROFILE) {
            EditSellerProfileScreen(
                onBack           = { navController.popBackStack() }
            )
        }

        composable(Routes.SELLER_PRODUCTS) { backStack ->
            val sellerBackStackEntry = remember(backStack) {
                navController.getBackStackEntry("seller_graph")
            }
            val sellerViewModel: SellerViewModel = viewModel(sellerBackStackEntry)

            ManageProductsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToAddProduct = { navController.navigate(Routes.SELLER_ADD_PRODUCT) },
                sellerViewModel = sellerViewModel
            )
        }

        composable(Routes.SELLER_EARNINGS) {
            EarningsHistoryScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
