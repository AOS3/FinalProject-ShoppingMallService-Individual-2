package com.teammeditalk.medicationproject.ui.search.drug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammeditalk.medicationproject.data.repository.DrugRepository

class SearchDrugViewModelFactory(
    private val drugRepository: DrugRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchDrugViewModel::class.java)) {
            return SearchDrugViewModel(drugRepository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
