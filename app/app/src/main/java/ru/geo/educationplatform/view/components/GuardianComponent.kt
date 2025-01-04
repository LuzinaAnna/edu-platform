package ru.geo.educationplatform.view.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.geo.educationplatform.view.viewmodel.GuardianViewModel

@Composable
fun GuardianComponent(
    navController: NavController,
    viewModel: GuardianViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> Log.e("GREET","ON_CREATE_GUARDIAN")
                Lifecycle.Event.ON_START -> Log.e("GREET","ON_START_GUARDIAN")
                Lifecycle.Event.ON_STOP -> Log.e("GREET","ON_STOP_GUARDIAN")
                Lifecycle.Event.ON_DESTROY -> Log.e("GREET","ON_DESTROY_GUARDIAN")
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    SideEffect {
        Log.e("GREET", "Entering GuardianComponent")
    }
    val isAuthenticated = viewModel.isAuthenticated()
    LaunchedEffect(isAuthenticated) {
        if (!isAuthenticated) {
            navController.navigate(Authentication) {
                popUpTo(Guardian)
            }
        } else {
            navController.navigate(Greeting) {
                popUpTo(Guardian)
            }
        }
    }
}