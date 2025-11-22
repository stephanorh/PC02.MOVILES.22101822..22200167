package dev.stephano.pc02moviles.presentation.navigation

import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.stephano.pc02moviles.presentation.register.RegistroScreen
import dev.stephano.pc02moviles.presentation.listado.ListadoScreen

@androidx.compose.runtime.Composable
fun AppNavGraph(){

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "register"){

        composable("register") { RegistroScreen(navController) }
        composable ("login") { Text("Login placeholder") }

        composable ("listado") { ListadoScreen(navController) }

        composable ("home") {
            DrawerScaffold(navController) {
                Text("Home placeholder")
            }
        }

    }
}