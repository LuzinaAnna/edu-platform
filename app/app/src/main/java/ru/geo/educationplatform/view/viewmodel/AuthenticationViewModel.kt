package ru.geo.educationplatform.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.geo.educationplatform.domain.auth.AuthenticationManager
import ru.geo.educationplatform.domain.auth.repository.container.AuthenticationStatus
import ru.geo.educationplatform.domain.auth.repository.container.Credentials
import ru.geo.educationplatform.view.components.Authentication
import ru.geo.educationplatform.view.components.Greeting
import ru.geo.educationplatform.view.viewmodel.state.CredentialsUiState
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager
): ViewModel() {
    private val _credentialsUiState = MutableStateFlow(CredentialsUiState("", ""))
    private val _authenticationResult = MutableStateFlow(AuthenticationStatus.DUMMY)
    private val _isAuthenticated = MutableStateFlow(false)

    val credentialsUiState = _credentialsUiState.asStateFlow()
    val authenticationStatus = _authenticationResult.asStateFlow()
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun setIsAuthenticated(isAuthenticated: Boolean) {
        _isAuthenticated.value = isAuthenticated
    }

    fun authenticate(onSuccessCallback: Runnable) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _credentialsUiState.value
            val credentials = Credentials(
                state.username,
                state.password
            )
            _authenticationResult.update { _ -> AuthenticationStatus.PROCESSING }
            val status = authenticationManager.auth(credentials)
            if (status == AuthenticationStatus.SUCCESSFUL) {
                launch(Dispatchers.Main) {
                    onSuccessCallback.run()
                }
            } else {
                _authenticationResult.update { _ -> status}
            }
        }
    }

    fun setUsername(username: String) {
        _credentialsUiState.update { prevState ->
            prevState.copy(
                username = username
            )
        }
    }

    fun setPassword(password: String) {
        _credentialsUiState.update { prevState ->
            prevState.copy(
                password = password
            )
        }
    }

    fun reset() {
        _credentialsUiState.update { _ ->
            CredentialsUiState(username = "", password = "")
        }
    }

    fun restartAuthentication() {
        _authenticationResult.update { _ -> AuthenticationStatus.DUMMY }
    }
}