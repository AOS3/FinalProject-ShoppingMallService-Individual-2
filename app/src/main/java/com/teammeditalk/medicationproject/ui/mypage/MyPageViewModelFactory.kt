package com.teammeditalk.medicationproject.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammeditalk.medicationproject.data.repository.DiseaseRepository
import com.teammeditalk.medicationproject.data.repository.MyAllergyRepository
import com.teammeditalk.medicationproject.data.repository.MyDiseaseRepository

// 종속 항목이 있는 뷰모델을 만들기 위함
class MyPageViewModelFactory(
    private val diseaseRepository: DiseaseRepository,
    private val myDiseaseRepository: MyDiseaseRepository,
    private val myAllergyRepository: MyAllergyRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPageViewModel::class.java)) {
            return MyPageViewModel(
                diseaseRepository = diseaseRepository,
                myAllergyRepository = myAllergyRepository,
                myDiseaseRepository = myDiseaseRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
