package com.teammeditalk.medicationproject.ui.allergy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammeditalk.medicationproject.data.repository.UserHealthInfoRepository
import com.teammeditalk.medicationproject.ui.mypage.MyPageViewModel

class SetAllergyViewModelFactory(
    private val userHealthInfoRepository: UserHealthInfoRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetAllergyViewModel::class.java)) {
            return SetAllergyViewModel(userHealthInfoRepository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
