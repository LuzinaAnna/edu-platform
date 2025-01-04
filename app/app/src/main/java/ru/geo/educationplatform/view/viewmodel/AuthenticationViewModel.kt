package ru.geo.educationplatform.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.geo.educationplatform.domain.auth.AuthenticationManager
import ru.geo.educationplatform.domain.auth.repository.container.AuthenticationStatus
import ru.geo.educationplatform.domain.auth.repository.container.Credentials
import ru.geo.educationplatform.view.viewmodel.state.CredentialsUiState
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager
): ViewModel() {
    private val _credentialsUiState = MutableStateFlow(CredentialsUiState("", ""))
    private val _authenticationResult = MutableStateFlow(AuthenticationStatus.DUMMY)
    val credentialsUiState = _credentialsUiState.asStateFlow()
    val authenticationStatus = _authenticationResult.asStateFlow()

    fun isAuthenticated(): Boolean {
        return authenticationManager.isAuthenticated()
    }

    fun authenticate() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _credentialsUiState.value
            val credentials = Credentials(
                state.username,
                state.password
            )
            _authenticationResult.update { _ -> AuthenticationStatus.PROCESSING }
            _authenticationResult.update { _ -> authenticationManager.auth(credentials) } 
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