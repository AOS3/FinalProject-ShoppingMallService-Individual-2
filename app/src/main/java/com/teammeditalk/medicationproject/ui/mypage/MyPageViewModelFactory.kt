package com.teammeditalk.medicationproject.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammeditalk.medicationproject.data.repository.UserHealthInfoRepository
import com.teammeditalk.medicationproject.data.repository.impl.DiseaseRepository

// 종속 항목이 있는 뷰모델을 만들기 위함
class MyPageViewModelFactory(
    private val diseaseRepository: DiseaseRepository,
    private val userHealthInfoRepository: UserHealthInfoRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPageViewModel::class.java)) {
            return MyPageViewModel(userHealthInfoRepository, diseaseRepository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
