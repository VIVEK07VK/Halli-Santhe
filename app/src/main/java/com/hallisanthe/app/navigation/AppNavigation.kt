package com.hallisanthe.app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hallisanthe.app.models.UserRole
import com.hallisanthe.app.ui.screens.*
import com.hallisanthe.app.viewmodel.AuthViewModel
import com.hallisanthe.app.viewmodel.CartViewModel
import com.hallisanthe.app.viewmodel.FavoriteViewModel
import com.hallisanthe.app.viewmodel.OrderTrackingViewModel
import com.hallisanthe.app.viewmodel.SellerViewModel
import com.hallisanthe.app.viewmodel.ProfileViewModel

// ─── Route constants ──────────────────────────────────────────────────────────

object Routes {
    const val SPLASH          = "splash"
    const val LOADING         = "loading"
    const val ROLE_SELECTION  = "role_selection"
    const val LOGIN           = "login/{role}"
    const val REGISTER        = "register/{role}"
    const val FORGOT_PASSWORD = "forgot_password"
    const val RESET_SENT      = "reset_sent"
    const val BUYER_HOME      = "buyer_home"
    const val BUYER_SEARCH    = "buyer_search"
    const val BUYER_FAVORITES = "buyer_favorites"
    const val BUYER_PROFILE   = "buyer_profile"
    const val PRODUCT_DETAILS = "product_details/{productId}"
    const val BUYER_ORDERS     = "buyer_orders"
    const val SELLER_DASHBOARD = "seller_dashboard"
    const val SELLER_ORDERS    = "seller_orders"
    const val SELLER_ADD_PRODUCT = "seller_add_product"
    const val SELLER_PROFILE   = "seller_profile"
    const val EDIT_PROFILE     = "edit_profile"
    const val SAVED_ADDRESSES  = "saved_addresses"
    const val RECENTLY_VIEWED  = "recently_viewed"
    const val HELP_SUPPORT     = "help_support"
    const val ABOUT            = "about"
    const val CART            = "cart"
    const val CHECKOUT        = "checkout"
    const val PAYMENT         = "payment"
    const val ORDER_CONFIRMATION = "order_confirmation/{orderId}"
    const val BUYER_TRACKING  = "buyer_tracking/{orderId}"

    fun login(role: UserRole)    = "login/${role.name}"
    fun register(role: UserRole) = "register/${role.name}"
    fun buyerTracking(orderId: String) = "buyer_tracking/$orderId"
    fun orderConfirmation(orderId: String) = "order_confirmation/$orderId"
    fun productDetails(productId: String) = "product_details/$productId"
}

// ─── Navigation graph ─────────────────────────────────────────────────────────

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Shared ViewModels scoped to the whole nav graph so session persists
    val authViewModel: AuthViewModel         = viewModel()
    val cartViewModel: CartViewModel         = viewModel()
    val favoriteViewModel: FavoriteViewModel = viewModel()
    val sellerViewModel: SellerViewModel     = viewModel()
    val trackingViewModel: OrderTrackingViewModel = viewModel()
    val profileViewModel: ProfileViewModel   = viewModel()

    NavHost(
        navController    = navController,
        startDestination  = Routes.LOADING,
        enterTransition   = { slideInHorizontally(tween(300)) { it } + fadeIn(tween(300)) },
        exitTransition    = { slideOutHorizontally(tween(300)) { -it } + fadeOut(tween(200)) },
        popEnterTransition  = { slideInHorizontally(tween(300)) { -it } + fadeIn(tween(300)) },
        popExitTransition   = { slideOutHorizontally(tween(300)) { it } + fadeOut(tween(200)) }
    ) {

        // ── Loading / Session check ──────────────────────────────────────
        composable(Routes.LOADING) {
            LoadingScreen(
                authViewModel             = authViewModel,
                onNavigateToBuyerHome     = {
                    navController.navigate(Routes.BUYER_HOME) {
                        popUpTo(Routes.LOADING) { inclusive = true }
                    }
                },
                onNavigateToSellerDashboard = {
                    navController.navigate(Routes.SELLER_DASHBOARD) {
                        popUpTo(Routes.LOADING) { inclusive = true }
                    }
                },
                onNavigateToRoleSelection = {
                    navController.navigate(Routes.ROLE_SELECTION) {
                        popUpTo(Routes.LOADING) { inclusive = true }
                    }
                }
            )
        }

        // ── Splash ───────────────────────────────────────────────────────
        composable(Routes.SPLASH) {
            SplashScreen(
                onExploreClicked = {
                    navController.navigate(Routes.ROLE_SELECTION) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // ── Shared Screens ───────────────────────────────────────────────
        composable(Routes.SAVED_ADDRESSES) {
            SavedAddressesScreen(
                profileViewModel = profileViewModel,
                onBack           = { navController.popBackStack() }
            )
        }

        composable(Routes.RECENTLY_VIEWED) {
            RecentlyViewedScreen(
                profileViewModel = profileViewModel,
                onBack           = { navController.popBackStack() }
            )
        }

        composable(Routes.HELP_SUPPORT) {
            HelpSupportScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.ABOUT) {
            AboutScreen(onBack = { navController.popBackStack() })
        }

        // ── Modular Nav Graphs ───────────────────────────────────────────
        authGraph(navController, authViewModel)
        buyerGraph(navController, authViewModel, cartViewModel, favoriteViewModel, trackingViewModel, profileViewModel)
        sellerGraph(navController, authViewModel, sellerViewModel, profileViewModel)
    }
}
