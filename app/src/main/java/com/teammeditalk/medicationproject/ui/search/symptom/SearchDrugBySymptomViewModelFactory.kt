package com.teammeditalk.medicationproject.ui.search.symptom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammeditalk.medicationproject.data.repository.DrugRepository

class SearchDrugBySymptomViewModelFactory(
    private val drugRepository: DrugRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchDrugBySymptomViewModel::class.java)) {
            return SearchDrugBySymptomViewModel(drugRepository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
