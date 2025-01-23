package com.teammeditalk.medicationproject.ui.search.disease

import androidx.lifecycle.ViewModel
import com.teammeditalk.medicationproject.data.repository.DiseaseRepository

class SearchViewModel(
    private val diseaseRepository: DiseaseRepository,
) : ViewModel()
