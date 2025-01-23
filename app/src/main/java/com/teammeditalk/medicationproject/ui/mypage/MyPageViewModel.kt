package com.teammeditalk.medicationproject.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicationproject.data.repository.DiseaseRepository
import com.teammeditalk.medicationproject.data.repository.UserHealthInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val userHealthInfoRepository: UserHealthInfoRepository,
    private val diseaseRepository: DiseaseRepository,
) : ViewModel() {
    val diseaseFLow = userHealthInfoRepository.diseaseFlow
    val allergyFlow = userHealthInfoRepository.allergyFlow

    init {
        viewModelScope.launch {
            userHealthInfoRepository.allergyFlow.collect {
                _allergyState.value = it
            }
            userHealthInfoRepository.diseaseFlow.collect {
                _diseaseState.value = it
            }
        }
    }

    private val _diseaseState = MutableStateFlow(emptyList<String>())
    val diseaseState = _diseaseState.asStateFlow()

    private val _allergyState = MutableStateFlow(emptyList<String>())
    val allergyState = _allergyState.asStateFlow()


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

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun reset() {
        _searchQuery.value = ""
        _searchResult.value = emptyList()
    }

    fun searchDiseaseName(searchText: String) {
        viewModelScope.launch {
            val result =
                diseaseRepository
                    .searchDiseaseName(searchText)
            _searchResult.value = result
        }
    }

    fun setAllergyInfo(allergyList: List<String>) {
        viewModelScope.launch {
            userHealthInfoRepository.saveAllergyInfo(allergyList.toSet())
            _allergyState.value = allergyList
        }
    }

    fun saveDiseaseInfo(diseaseList: List<String>) {
        viewModelScope.launch {
            userHealthInfoRepository.saveDiseaseInfo(diseaseList)
            _diseaseState.value = diseaseList
        }
    }
}
