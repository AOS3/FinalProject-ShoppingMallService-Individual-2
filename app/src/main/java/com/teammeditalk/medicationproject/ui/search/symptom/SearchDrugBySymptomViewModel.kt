package com.teammeditalk.medicationproject.ui.search.symptom

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicationproject.data.model.Item
import com.teammeditalk.medicationproject.data.repository.DrugRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchDrugBySymptomViewModel(
    private val drugRepository: DrugRepository,
) : ViewModel() {
    private val _drugInfo = MutableStateFlow(emptyList<Item>())
    val drugInfo = _drugInfo.asStateFlow()

    fun searchDrugBySymptom(symptom: String) {
        viewModelScope.launch {
            drugRepository.getDrugBySymptom(symptom)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private val _searchQuery =
        MutableStateFlow("").apply {
            Log.d("쿼리", this.value)
        }
    val searchQuery =
        _searchQuery.asStateFlow()

    private val _searchResult =
        MutableStateFlow(emptyList<String?>()).apply {
            Log.d("결과", this.value.toString())
        }
    val searchResult = _searchResult.asStateFlow()

    fun reset() {
        _searchQuery.value = ""
        _searchResult.value = emptyList()
    }
}
