package ru.geo.educationplatform.view.components

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.geo.educationplatform.view.viewmodel.GreetingViewModel

@Composable
fun GreetingComponent(
    navController: NavController,
    viewModel: GreetingViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> Log.e("GREET","ON_CREATE_GREETING")
                Lifecycle.Event.ON_START -> Log.e("GREET","ON_START_GREETING")
                Lifecycle.Event.ON_STOP -> Log.e("GREET","ON_STOP_GREETING")
                Lifecycle.Event.ON_DESTROY -> Log.e("GREET","ON_DESTROY_GREETING")
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    SideEffect {
        Log.e("GREET", "Entering GreetingComponent")
    }
    val greeting by viewModel.greetingState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            viewModel.greeting()
        }
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        Text(
            text = greeting,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}