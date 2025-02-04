package com.teammeditalk.medicationproject.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammeditalk.medicationproject.data.repository.AuthRepository

class CartViewModelFactory(
    private val authRepository: AuthRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(
                authRepository = authRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
