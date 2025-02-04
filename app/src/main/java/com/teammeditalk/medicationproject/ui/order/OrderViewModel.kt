package com.teammeditalk.medicationproject.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.teammeditalk.medicationproject.data.repository.AuthRepository
import kotlinx.coroutines.launch

class OrderViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private var uuid: String? = null

    init {
        viewModelScope.launch {
            authRepository.uuid.collect {
                uuid = it
            }
        }
    }

    fun cancelOrder() {
        val db = Firebase.firestore
        if (uuid == null) return
        db.collection(uuid!!)
    }
}
