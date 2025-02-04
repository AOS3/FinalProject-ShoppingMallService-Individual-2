package com.teammeditalk.medicationproject.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammeditalk.medicationproject.data.repository.AuthRepository
import com.teammeditalk.medicationproject.data.repository.MyAllergyRepository
import com.teammeditalk.medicationproject.data.repository.MyDiseaseRepository
import com.teammeditalk.medicationproject.data.repository.MyDrugRepository

class CartViewModelFactory(
    private val myDiseaseRepository: MyDiseaseRepository,
    private val myDrugRepository: MyDrugRepository,
    private val myAllergyRepository: MyAllergyRepository,
    private val authRepository: AuthRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(
                myDiseaseRepository,
                myDrugRepository,
                myAllergyRepository,
                authRepository,
            )
                as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
