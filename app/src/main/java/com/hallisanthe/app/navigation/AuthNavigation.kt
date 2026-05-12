package com.hallisanthe.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hallisanthe.app.models.UserRole
import com.hallisanthe.app.ui.screens.*
import com.hallisanthe.app.viewmodel.AuthViewModel

fun NavGraphBuilder.authGraph(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    navigation(startDestination = Routes.ROLE_SELECTION, route = "auth_graph") {
        
        composable(Routes.ROLE_SELECTION) {
            RoleSelectionScreen(
                onBuyerSelected  = { navController.navigate(Routes.login(UserRole.BUYER)) },
                onSellerSelected = { navController.navigate(Routes.login(UserRole.SELLER)) }
            )
        }

        composable(
            route = Routes.LOGIN,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStack ->
            val roleName = backStack.arguments?.getString("role") ?: UserRole.BUYER.name
            val role = UserRole.values().firstOrNull { it.name == roleName } ?: UserRole.BUYER
            LoginScreen(
                role             = role,
                authViewModel    = authViewModel,
                onLoginSuccess   = { userRole ->
                    val dest = if (userRole == UserRole.SELLER) Routes.SELLER_DASHBOARD else Routes.BUYER_HOME
                    navController.navigate(dest) {
                        popUpTo(Routes.ROLE_SELECTION) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Routes.register(role)) },
                onNavigateToForgotPassword = { navController.navigate(Routes.FORGOT_PASSWORD) },
                onBack           = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.REGISTER,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStack ->
            val roleName = backStack.arguments?.getString("role") ?: UserRole.BUYER.name
            val role = UserRole.values().firstOrNull { it.name == roleName } ?: UserRole.BUYER
            RegisterScreen(
                role              = role,
                authViewModel     = authViewModel,
                onRegisterSuccess = { userRole ->
                    val dest = if (userRole == UserRole.SELLER) Routes.SELLER_DASHBOARD else Routes.BUYER_HOME
                    navController.navigate(dest) {
                        popUpTo(Routes.ROLE_SELECTION) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() },
                onBack            = { navController.popBackStack() }
            )
        }

        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                authViewModel = authViewModel,
                onBack        = { navController.popBackStack() },
                onResetSent   = {
                    navController.navigate(Routes.RESET_SENT) {
                        popUpTo(Routes.FORGOT_PASSWORD) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.RESET_SENT) {
            ResetEmailSentScreen(
                onBackToLogin = {
                    navController.navigate(Routes.ROLE_SELECTION) {
                        popUpTo(Routes.RESET_SENT) { inclusive = true }
                    }
                }
            )
        }
    }
}
