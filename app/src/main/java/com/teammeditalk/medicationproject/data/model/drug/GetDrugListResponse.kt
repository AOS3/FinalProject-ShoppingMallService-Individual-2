package com.teammeditalk.medicationproject.data.model.drug

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("response")
data class GetDrugListResponse(
    val header: DrugHeader,
    val body: DrugBody,
)

@Serializable
@SerialName("header")
data class DrugHeader(
    val resultCode: String,
    val resultMsg: String,
)

@Serializable
@SerialName("body")
data class DrugBody(
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int,
    val items: List<DrugItem>,
)

@Serializable
data class DrugItem(
    val entpName: String,
    val itemSeq: String,
    val itemName: String,
    val efcyQesitm: String,
    val useMethodQesitm: String,
    val atpnWarnQesitm: String,
    val atpnQesitm: String,
    val intrcQesitm: String,
    val seQesitm: String,
    val depositMethodQesitm: String,
    val openDe: String,
    val updateDe: String,
    val itemImage: String,
)
