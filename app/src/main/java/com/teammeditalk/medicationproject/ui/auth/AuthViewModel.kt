package com.teammeditalk.medicationproject.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicationproject.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    fun saveUuid(uuid: String) {
        viewModelScope.launch {
            authRepository.saveUuid(uuid)
        }
    }
}
