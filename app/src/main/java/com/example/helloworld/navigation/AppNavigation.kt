package com.example.helloworld.navigation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.helloworld.view.ScreenConversa
import com.example.helloworld.view.ScreenChatbot
import com.example.helloworld.view.ScreenChatbotHistorico

object AppRoutes {
    const val Inicio = "inicio"
    const val Conversa = "conversa"
    const val MeusBlooms = "meus_blooms"
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Conversa,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(AppRoutes.Inicio) {
                ScreenChatbot(
                    onNavigateToConversa = { navController.navigateSingleTop(AppRoutes.Conversa) }
                )
            }

            composable(AppRoutes.Conversa) {
                ScreenConversa(
                    onNavigateToInicio = {
                        navController.navigate(AppRoutes.Inicio) {
                            popUpTo(AppRoutes.Conversa) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToMeusBlooms = { navController.navigateSingleTop(AppRoutes.MeusBlooms) }
                )
            }

            composable(AppRoutes.MeusBlooms) {
                ScreenChatbotHistorico(
                    onNavigateToConversa = { navController.navigateSingleTop(AppRoutes.Conversa) }
                )
            }
        }
    }
}

private fun NavHostController.navigateSingleTop(route: String) {
    navigate(route) {
        launchSingleTop = true
    }
}
