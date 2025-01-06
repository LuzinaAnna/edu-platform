package ru.geo.educationplatform.view.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import okhttp3.OkHttpClient
import ru.geo.educationplatform.R
import ru.geo.educationplatform.data.auth.api.AuthenticationApiImpl
import ru.geo.educationplatform.data.auth.repository.JwtStoreRepositoryImpl
import ru.geo.educationplatform.domain.auth.AuthenticationManager
import ru.geo.educationplatform.domain.auth.repository.container.AuthenticationStatus
import ru.geo.educationplatform.view.viewmodel.AuthenticationViewModel

@Composable
fun AuthenticationComponent(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    val credentialsState by viewModel.credentialsUiState.collectAsState()
    val authenticationStatus by viewModel.authenticationStatus.collectAsState()

    var showPassword by remember { mutableStateOf(false) }

    val ctx = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (authenticationStatus) {
                AuthenticationStatus.PROCESSING -> {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                AuthenticationStatus.SERVER_ERROR -> {
                    Toast.makeText(
                        ctx,
                        R.string.failed_auth_server_error,
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.restartAuthentication()
                }

                AuthenticationStatus.INVALID_CREDENTIALS -> {
                    viewModel.reset()
                    Toast.makeText(
                        ctx,
                        R.string.failed_auth_invalid_creds,
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.restartAuthentication()
                }

                else -> {
                    Box (
                        modifier = Modifier
                            .padding(bottom = 350.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .width(250.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.app_name),
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = credentialsState.username,
                                placeholder = { Text(stringResource(R.string.username_placeholder)) },
                                onValueChange = { viewModel.setUsername(it) },
                                singleLine = true,
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = credentialsState.password,
                                placeholder = { Text(stringResource(R.string.password_placeholder)) },
                                onValueChange = { viewModel.setPassword(it) },
                                singleLine = true,
                                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    val image = if (showPassword)
                                        Icons.Filled.Visibility
                                    else Icons.Filled.VisibilityOff

                                    val description =
                                        if (showPassword) "Hide password" else "Show password"
                                    IconButton(onClick = { showPassword = !showPassword }) {
                                        Icon(imageVector = image, description)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Button(
                                onClick = {
                                    viewModel.authenticate {
                                        viewModel.setIsAuthenticated(true)
                                        navController.navigate(Greeting)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = stringResource(R.string.authenticate_button_lbl),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAuthenticationComponent() {
    val navController = rememberNavController()
    AuthenticationComponent(
        navController,
        AuthenticationViewModel(
            AuthenticationManager(
                JwtStoreRepositoryImpl(),
                AuthenticationApiImpl(
                    OkHttpClient()
                )
            )
        )
    )
}