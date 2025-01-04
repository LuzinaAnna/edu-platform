package ru.geo.educationplatform.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import ru.geo.educationplatform.view.viewmodel.GreetingViewModel

@Serializable
object Guardian

@Serializable
object Greeting

@Serializable
object Authentication

@Composable
fun NavigationComponent(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Guardian) {
        composable<Guardian> {
            GuardianComponent(navController)
        }
        composable<Greeting> {
            GreetingComponent(navController)
        }
        composable<Authentication> {
            AuthenticationComponent(navController)
        }
    }
}