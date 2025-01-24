package com.teammeditalk.medicationproject.ui.detail

import androidx.lifecycle.ViewModel
import com.teammeditalk.medicationproject.data.repository.DrugRepository

class DetailViewModel(
    private val drugRepository: DrugRepository,
) : ViewModel()
