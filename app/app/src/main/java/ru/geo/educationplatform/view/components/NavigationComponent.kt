package ru.geo.educationplatform.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import ru.geo.educationplatform.view.viewmodel.AuthenticationViewModel

@Serializable
object Greeting

@Serializable
object Authentication

@Composable
fun NavigationComponent(
    modifier: Modifier,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()

    val startDestination: Any = if (isAuthenticated) {
        Greeting
    } else {
        Authentication
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable<Greeting> {
            GreetingComponent(navController, modifier)
        }
        composable<Authentication> {
            AuthenticationComponent(navController)
        }
    }
}