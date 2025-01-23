package com.teammeditalk.medicationproject.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.teammeditalk.medicationproject.data.repository.DrugRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val drugRepository: DrugRepository,
) : ViewModel() {

}