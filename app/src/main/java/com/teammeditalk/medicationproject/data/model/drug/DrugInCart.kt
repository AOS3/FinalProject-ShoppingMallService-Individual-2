package com.teammeditalk.medicationproject.data.model.drug

// 장바구니 약 아이템
data class DrugInCart(
    val id: String,
    val userId: String,
    val drugName: String,
    val drugImageUri: String,
)
