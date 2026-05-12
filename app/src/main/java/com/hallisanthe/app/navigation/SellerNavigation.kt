package com.hallisanthe.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hallisanthe.app.ui.screens.*
import com.hallisanthe.app.viewmodel.AuthViewModel
import com.hallisanthe.app.viewmodel.ProfileViewModel
import com.hallisanthe.app.viewmodel.SellerViewModel

fun NavGraphBuilder.sellerGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
    sellerViewModel: SellerViewModel,
    profileViewModel: ProfileViewModel
) {
    navigation(startDestination = Routes.SELLER_DASHBOARD, route = "seller_graph") {
        
        composable(Routes.SELLER_DASHBOARD) {
            SellerDashboardScreen(
                sellerViewModel    = sellerViewModel,
                onNavigateToOrders = { navController.navigate(Routes.SELLER_ORDERS) },
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
            SellerOrdersScreen(
                sellerViewModel = sellerViewModel,
                onBack = { navController.popBackStack() }
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
                profileViewModel = profileViewModel,
                onEditProfile    = { navController.navigate(Routes.EDIT_PROFILE) },
                onMyOrders       = { navController.navigate(Routes.SELLER_ORDERS) },
                onSavedAddresses = { navController.navigate(Routes.SAVED_ADDRESSES) },
                onRecentlyViewed = { navController.navigate(Routes.RECENTLY_VIEWED) },
                onHelpSupport    = { navController.navigate(Routes.HELP_SUPPORT) },
                onAbout          = { navController.navigate(Routes.ABOUT) },
                onLogout         = {
                    authViewModel.logout()
                    navController.navigate(Routes.ROLE_SELECTION) { popUpTo(0) { inclusive = true } }
                }
            )
        }

        composable(Routes.EDIT_PROFILE) {
            EditProfileScreen(
                profileViewModel = profileViewModel,
                onBack           = { navController.popBackStack() }
            )
        }
    }
}
