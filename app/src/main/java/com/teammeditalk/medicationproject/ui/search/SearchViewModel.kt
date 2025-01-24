package com.teammeditalk.medicationproject.ui.search

import androidx.lifecycle.ViewModel
import com.teammeditalk.medicationproject.data.repository.MyDiseaseRepository

class SearchViewModel(
    private val myDiseaseRepository: MyDiseaseRepository,
) : ViewModel()
