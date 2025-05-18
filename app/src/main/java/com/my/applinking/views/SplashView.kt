package com.my.applinking.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.my.applinking.routes.AppRoutes
import kotlinx.coroutines.delay

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
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it), contentAlignment = Alignment.Center) {
            Text("Splash View")
        }
    }
}