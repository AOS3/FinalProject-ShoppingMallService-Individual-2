package com.teammeditalk.medicationproject.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicationproject.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CartViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private var uuid: String? = null

    // 삭제 여부
    private val _isDeleted = MutableStateFlow(false)
    val isDeleted = _isDeleted.asStateFlow()

    private val _drugList = MutableStateFlow<List<Drug>>(emptyList())
    val drugList = _drugList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.uuid.collectLatest {
                uuid = it
            }
        }
    }

    suspend fun deleteDrugItemInCart(drug: List<Drug>) {
        viewModelScope.launch {
            val db = Firebase.firestore
            drug.forEach {
                db
                    .collection(uuid!!)
                    .document(it.id)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("약 삭제", "DocumentSnapshot successfully deleted!")
                        _drugList.value -= drug
                        _isDeleted.value = true
                    }.addOnFailureListener { e ->
                        Log.w("약 삭제", "Error deleting document", e)
                        _isDeleted.value = false
                    }
            }
        }
    }

    suspend fun getDrugItemInCart() {
        suspendCoroutine { continuation ->
            val db = Firebase.firestore
            val drugData = db.collection(uuid!!)
            drugData
                .get()
                .addOnSuccessListener { snapshot ->
                    val drugList =
                        // todo :mapNotNull vs map
                        snapshot.documents.map { document ->
                            val data = document.data
                            Drug(
                                id = document.id,
                                drugImageUri = data?.get("imageUri").toString(),
                                drugName = data?.get("name").toString(),
                                userId = data?.get("user_id").toString(),
                            )
                        }

                    _drugList.value = drugList
                    // 성공했을 경우
                    continuation.resume(drugList)
                    _isLoading.value = false
                }.addOnFailureListener { e ->
                    e.printStackTrace()
                    // 실패했을 경우
                    continuation.resumeWithException(e)
                    _isLoading.value = false
                }
        }
    }
}
