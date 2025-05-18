package com.my.applinking.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.my.applinking.routes.AppRoutes
import com.my.applinking.viewmodel.NavigationViewModel
import com.my.applinking.views.CategoryView
import com.my.applinking.views.DashboardView
import com.my.applinking.views.ExpenseView
import com.my.applinking.views.FundView
import com.my.applinking.views.SplashView
import kotlinx.coroutines.delay

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: NavigationViewModel = hiltViewModel()
    val deepLinkId = viewModel.deepLinkId.collectAsState()
    var coldStart by remember { mutableStateOf(true) }

    LaunchedEffect(deepLinkId.value) {
        if (coldStart) {
            delay(2000)
            coldStart = false
        }

        if (deepLinkId.value != null) {
            deepLinkId.value.let { id ->
                if(navController.currentDestination?.route == "${AppRoutes.EXPENSE.route}/{itemId}"){
                    navController.popBackStack(AppRoutes.DASHBOARD.route, inclusive = false)
                }
                navController.navigate("${AppRoutes.EXPENSE.route}/$id")
                Log.d("APP-LINKING-11", "handleIntent: $id")
            }
            viewModel.clearDeeplinkId()
        }
    }

    NavHost(navController, startDestination = AppRoutes.SPLASH.route) {
        composable(AppRoutes.SPLASH.route) { SplashView(navController) }
        composable(AppRoutes.DASHBOARD.route) { DashboardView(navController) }
        composable(
            "${AppRoutes.EXPENSE.route}/{itemId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            ExpenseView(navController, itemId)
        }
        composable(AppRoutes.FUND.route) { FundView(navController) }
        composable(AppRoutes.CATEGORY.route) { CategoryView(navController) }
    }
}