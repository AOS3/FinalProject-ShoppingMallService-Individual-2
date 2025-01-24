package com.teammeditalk.medicationproject.ui.allergy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammeditalk.medicationproject.data.repository.MyAllergyRepository

class SetAllergyViewModelFactory(
    private val myAllergyRepository: MyAllergyRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetAllergyViewModel::class.java)) {
            return SetAllergyViewModel(myAllergyRepository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
