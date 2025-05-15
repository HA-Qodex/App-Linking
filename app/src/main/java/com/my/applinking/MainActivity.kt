package com.my.applinking

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.my.applinking.ui.theme.AppLinkingTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppLinkingTheme {
                AppNavigation()
            }
        }
    }
}

sealed class AppRoutes(
    val route: String
) {
    data object SPLASH : AppRoutes("splash")
    data object DASHBOARD : AppRoutes("dashboard")
    data object EXPENSE : AppRoutes("expense")
    data object FUND : AppRoutes("fund")
    data object CATEGORY : AppRoutes("category")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = AppRoutes.SPLASH.route) {
        composable(AppRoutes.SPLASH.route) { SplashView(navController) }
        composable(AppRoutes.DASHBOARD.route) { DashboardView(navController) }
        composable(
            "${AppRoutes.EXPENSE.route}/{itemId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType }
            )
//            deepLinks = listOf(
//                navDeepLink {
//                    uriPattern = "https://expense-tracker-e90c0.web.app/path/{itemId}"
//                }
//            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            ExpenseView(navController, itemId)
        }
        composable(AppRoutes.FUND.route) { FundView(navController) }
        composable(AppRoutes.CATEGORY.route) { CategoryView(navController) }
    }
}

@Composable
fun SplashView(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate(AppRoutes.DASHBOARD.route){
            popUpTo(0){
                inclusive = true
            }
        }
    }
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            Text("Splash View")
        }
    }
}

@Composable
fun DashboardView(navController: NavController) {
    val activity = LocalActivity.current
    val deepLinkItemId = activity?.intent?.data?.lastPathSegment

    LaunchedEffect(Unit) {
        if (!deepLinkItemId.isNullOrBlank()) {
            delay(1000)
            navController.navigate("${AppRoutes.EXPENSE.route}/$deepLinkItemId")
            activity.intent?.data = null
        }
    }
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            Text("Dashboard View")
        }
    }
}

@Composable
fun ExpenseView(navController: NavController, id: String? = null) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            Text("Expense View $id")
        }
    }
}

@Composable
fun FundView(navController: NavController) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            Text("Fund View")
        }
    }
}

@Composable
fun CategoryView(navController: NavController) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            Text("Category View")
        }
    }
}