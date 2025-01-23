package com.teammeditalk.medicationproject.ui.allergy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicationproject.data.repository.UserHealthInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SetAllergyViewModel(
    private val userHealthInfoRepository: UserHealthInfoRepository,
) : ViewModel() {
    private val _allergyState = MutableStateFlow(emptyList<String>())

    val allergyState = _allergyState.asStateFlow()

    init {
        viewModelScope.launch {
            userHealthInfoRepository.allergyFlow.collect {
                _allergyState.value = it
            }
        }
    }

    fun setAllergyInfo(allergyList: List<String>) {
        viewModelScope.launch {
            userHealthInfoRepository.saveAllergyInfo(allergyList.toSet())
        }
    }
}
