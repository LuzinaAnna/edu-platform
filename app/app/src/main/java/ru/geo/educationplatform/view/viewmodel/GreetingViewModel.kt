package ru.geo.educationplatform.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.geo.educationplatform.domain.greeting.usecase.GreetingUseCase
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val greetingUseCase: GreetingUseCase
) : ViewModel() {

    private val _greetingState = MutableStateFlow("")
    val greetingState = _greetingState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    suspend fun greeting() {
        _isLoading.value = true
        try {
            _greetingState.value = greetingUseCase.greeting()
        } finally {
            _isLoading.value = false
        }
    }
}