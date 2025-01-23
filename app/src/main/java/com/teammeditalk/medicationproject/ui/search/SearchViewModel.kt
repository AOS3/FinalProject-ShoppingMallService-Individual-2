package com.teammeditalk.medicationproject.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicationproject.data.repository.impl.DiseaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val diseaseRepository: DiseaseRepository,
) : ViewModel() {

}
