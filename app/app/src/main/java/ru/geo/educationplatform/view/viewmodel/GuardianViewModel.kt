package ru.geo.educationplatform.view.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.geo.educationplatform.domain.auth.AuthenticationManager
import javax.inject.Inject

@HiltViewModel
class GuardianViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager
): ViewModel() {
    fun isAuthenticated(): Boolean {
        return authenticationManager.isAuthenticated()
    }
}