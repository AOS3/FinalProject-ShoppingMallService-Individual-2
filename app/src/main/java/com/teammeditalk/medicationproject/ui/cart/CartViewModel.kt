package com.teammeditalk.medicationproject.ui.cart

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicationproject.data.model.drug.DrugInCart
import com.teammeditalk.medicationproject.data.model.order.OrderRequest
import com.teammeditalk.medicationproject.data.repository.AuthRepository
import com.teammeditalk.medicationproject.data.repository.MyAllergyRepository
import com.teammeditalk.medicationproject.data.repository.MyDiseaseRepository
import com.teammeditalk.medicationproject.data.repository.MyDrugRepository
import com.teammeditalk.medicationproject.ui.component.CustomOrderDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CartViewModel(
    private val myDiseaseRepository: MyDiseaseRepository,
    private val myDrugRepository: MyDrugRepository,
    private val myAllergyRepository: MyAllergyRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _selectedDrugList = MutableStateFlow(emptyList<DrugInCart>())
    val selectedDrugList = _selectedDrugList.asStateFlow()

    fun setSelectedDrugList(selectedDrugList: List<DrugInCart>) {
        _selectedDrugList.value = selectedDrugList
    }

    val customOrderDialogState: MutableState<CustomOrderDialogState> =
        mutableStateOf<CustomOrderDialogState>(
            CustomOrderDialogState(),
        )

    private var uuid: String? = null
    private val _myDrugList =
        MutableStateFlow(
            emptyList<String>(),
        )
    val myDrugList = _myDrugList.asStateFlow()

    private val _myDiseaseList =
        MutableStateFlow(
            emptyList<String>(),
        )
    val myDiseaseList = _myDiseaseList.asStateFlow()

    private val _myAllergyList =
        MutableStateFlow(
            emptyList<String>(),
        )
    val myAllergyList = _myAllergyList.asStateFlow()

    // 삭제 여부
    private val _isDeleted = MutableStateFlow(false)
    val isDeleted = _isDeleted.asStateFlow()

    private val _drugList = MutableStateFlow<List<DrugInCart>>(emptyList())
    val drugList = _drugList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.uuid.collectLatest {
                uuid = it
            }
        }
        viewModelScope.launch {
            myAllergyRepository.allergyFlow.collect {
                _myAllergyList.value = it
            }
        }
        viewModelScope.launch {
            myDiseaseRepository.diseaseFlow.collect {
                _myDiseaseList.value = it
            }
        }
        viewModelScope.launch {
            myDrugRepository.drugFlow.collect {
                _myDrugList.value = it
            }
        }
    }

    private fun order(orderRequest: OrderRequest) {
        val order =
            hashMapOf(
                "orderDate" to orderRequest.timeStamp,
                "allergyList" to orderRequest.allergyList,
                "diseaseList" to orderRequest.diseaseList,
                "drugList" to orderRequest.drugList,
                "orderDrugList" to orderRequest.orderDrugList,
                "message" to orderRequest.message,
            )

        viewModelScope.launch {
            val db = Firebase.firestore
            db
                .collection("order_$uuid")
                .add(order)
                .addOnSuccessListener {
                    Log.d("주문", "DocumentSnapshot added with ID: $it")
                    resetDialogState()
                }.addOnFailureListener { e ->
                    Log.w("주문", "Error adding document", e)
                }
        }
    }

    fun getCurrentTimeStamp(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    fun showCustomOrderDialog() {
        if (uuid != null) {
            customOrderDialogState.value =
                CustomOrderDialogState(
                    allergyList = _myAllergyList.value,
                    diseaseList = _myDiseaseList.value,
                    drugList = _myDrugList.value,
                    orderDrugList = _selectedDrugList.value,
                    onClickConfirm = { message ->
                        order(
                            OrderRequest(
                                timeStamp = getCurrentTimeStamp(),
                                userId = uuid!!,
                                allergyList = _myAllergyList.value,
                                diseaseList = _myDiseaseList.value,
                                drugList = _myDrugList.value,
                                message = message,
                                orderDrugList = _selectedDrugList.value,
                            ),
                        )
                    },
                    onClickCancel = {
                        resetDialogState()
                    },
                )
        }
    }

    fun resetDialogState() {
        customOrderDialogState.value = CustomOrderDialogState()
    }

    fun deleteDrugItemInCart(drug: List<DrugInCart>) {
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
                            DrugInCart(
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
