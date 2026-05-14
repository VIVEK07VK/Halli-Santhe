package com.hallisanthe.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hallisanthe.app.ui.screens.*
import com.hallisanthe.app.models.*
import com.hallisanthe.app.viewmodel.*
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel

fun NavGraphBuilder.buyerGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    favoriteViewModel: FavoriteViewModel,
    ordersViewModel: OrdersViewModel,
    profileViewModel: ProfileViewModel
) {
    navigation(startDestination = Routes.BUYER_HOME, route = "buyer_graph") {

        composable(Routes.BUYER_HOME) { backStack ->
            val buyerBackStackEntry = remember(backStack) {
                navController.getBackStackEntry("buyer_graph")
            }
            val homeViewModel: HomeViewModel = viewModel(buyerBackStackEntry)
            
            BuyerHomeScreen(
                authViewModel       = authViewModel,
                cartViewModel       = cartViewModel,
                homeViewModel       = homeViewModel,
                favoriteViewModel   = favoriteViewModel,
                onNavigateToCart    = {
                    navController.navigate(Routes.CART) {
                        popUpTo(Routes.BUYER_HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                onNavigateToSearch  = {
                    navController.navigate(Routes.BUYER_SEARCH) {
                        popUpTo(Routes.BUYER_HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                onNavigateToFavorites = {
                    navController.navigate(Routes.BUYER_FAVORITES) {
                        popUpTo(Routes.BUYER_HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                onNavigateToProfile = {
                    navController.navigate(Routes.BUYER_PROFILE) {
                        popUpTo(Routes.BUYER_HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                onProductClick = { productId -> navController.navigate(Routes.productDetails(productId)) },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.ROLE_SELECTION) { popUpTo(0) { inclusive = true } }
                }
            )
        }

        composable(Routes.BUYER_SEARCH) { backStack ->
            val buyerBackStackEntry = remember(backStack) {
                navController.getBackStackEntry("buyer_graph")
            }
            val homeViewModel: HomeViewModel     = viewModel(buyerBackStackEntry)
            val searchViewModel: SearchViewModel = viewModel(buyerBackStackEntry)

            SearchScreen(
                searchViewModel   = searchViewModel,
                homeViewModel     = homeViewModel,
                cartViewModel     = cartViewModel,
                favoriteViewModel = favoriteViewModel,
                onProductClick    = { productId -> navController.navigate(Routes.productDetails(productId)) },
                onNavigateToHome  = { navController.navigate(Routes.BUYER_HOME) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToSearch = { },
                onNavigateToFavorites = { navController.navigate(Routes.BUYER_FAVORITES) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToCart = { navController.navigate(Routes.CART) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToProfile = { navController.navigate(Routes.BUYER_PROFILE) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } }
            )
        }

        composable(Routes.BUYER_FAVORITES) {
            FavoritesScreen(
                favoriteViewModel     = favoriteViewModel,
                cartViewModel         = cartViewModel,
                onBack                = { navController.popBackStack() },
                onProductClick        = { productId -> navController.navigate(Routes.productDetails(productId)) },
                onNavigateToHome      = { navController.navigate(Routes.BUYER_HOME) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToSearch    = { navController.navigate(Routes.BUYER_SEARCH) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToFavorites = { },
                onNavigateToCart      = { navController.navigate(Routes.CART) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToProfile   = { navController.navigate(Routes.BUYER_PROFILE) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } }
            )
        }

        composable(Routes.BUYER_PROFILE) {
            ProfileScreen(
                authViewModel         = authViewModel,
                cartViewModel         = cartViewModel,
                profileViewModel      = profileViewModel,
                onBack                = { navController.popBackStack() },
                onLogout              = {
                    authViewModel.logout()
                    navController.navigate(Routes.ROLE_SELECTION) { popUpTo(0) { inclusive = true } }
                },
                onNavigateToHome      = { navController.navigate(Routes.BUYER_HOME) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToSearch    = { navController.navigate(Routes.BUYER_SEARCH) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToFavorites = { navController.navigate(Routes.BUYER_FAVORITES) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToCart      = { navController.navigate(Routes.CART) { popUpTo(Routes.BUYER_HOME) { saveState = true }; launchSingleTop = true; restoreState = true } },
                onNavigateToProfile   = { },
                onNavigateToOrders    = { navController.navigate(Routes.BUYER_ORDERS) },
                onNavigateToAddresses = { navController.navigate(Routes.SAVED_ADDRESSES) },
                onNavigateToRecentlyViewed = { navController.navigate(Routes.RECENTLY_VIEWED) },
                onNavigateToHelp      = { navController.navigate(Routes.HELP_SUPPORT) },
                onNavigateToAbout     = { navController.navigate(Routes.ABOUT) },
                onNavigateToEditProfile = { navController.navigate(Routes.BUYER_EDIT_PROFILE) }
            )
        }

        composable(Routes.BUYER_EDIT_PROFILE) {
            EditProfileScreen(
                profileViewModel = profileViewModel,
                onBack           = { navController.popBackStack() }
            )
        }

        composable(Routes.BUYER_ORDERS) {
            BuyerOrdersScreen(
                onBack       = { navController.popBackStack() },
                onTrackOrder = { orderId -> navController.navigate(Routes.buyerTracking(orderId)) }
            )
        }

        composable(
            route     = Routes.PRODUCT_DETAILS,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStack ->
            val productId   = backStack.arguments?.getString("productId") ?: ""
            val buyerBackStackEntry = remember(backStack) {
                navController.getBackStackEntry("buyer_graph")
            }
            val homeViewModel: HomeViewModel = viewModel(buyerBackStackEntry)
            val product = homeViewModel.getProductById(productId)

            ProductDetailsScreen(
                product           = product,
                cartViewModel     = cartViewModel,
                favoriteViewModel = favoriteViewModel,
                onBack            = { navController.popBackStack() }
            )
        }

        composable(Routes.CART) {
            CartScreen(
                cartViewModel = cartViewModel,
                onBack        = { navController.popBackStack() },
                onCheckout    = { navController.navigate(Routes.CHECKOUT) }
            )
        }

        composable(Routes.CHECKOUT) {
            CheckoutScreen(
                cartViewModel      = cartViewModel,
                onBack             = { navController.popBackStack() },
                onProceedToPayment = { navController.navigate(Routes.PAYMENT) }
            )
        }

        composable(Routes.PAYMENT) {
            PaymentScreen(
                cartViewModel    = cartViewModel,
                onBack           = { navController.popBackStack() },
                onPaymentSuccess = { orderId ->
                    navController.navigate(Routes.orderConfirmation(orderId)) {
                        popUpTo(Routes.CART) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route     = Routes.ORDER_CONFIRMATION,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStack ->
            val orderId = backStack.arguments?.getString("orderId") ?: ""
            OrderConfirmationScreen(
                orderId     = orderId,
                ordersViewModel = ordersViewModel,
                onBackToHome = {
                    navController.navigate(Routes.BUYER_HOME) { popUpTo(0) { inclusive = true } }
                },
                onTrackOrder = {
                    navController.navigate(Routes.buyerTracking(orderId))
                }
            )
        }

        composable(
            route     = Routes.BUYER_TRACKING,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStack ->
            val orderId = backStack.arguments?.getString("orderId") ?: ""
            BuyerTrackingScreen(
                orderId       = orderId,
                ordersViewModel = ordersViewModel,
                onBack        = { navController.popBackStack() }
            )
        }
    }
}
