package com.teammeditalk.medicationproject.data.model.order

import com.teammeditalk.medicationproject.data.model.drug.DrugInCart

// 주문 요청
data class OrderRequest(
    val timeStamp: String,
    val userId: String,
    val allergyList: List<String>,
    val diseaseList: List<String>,
    val drugList: List<String>,
    val orderDrugList: List<DrugInCart>,
    val message: String,
)
