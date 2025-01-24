package com.teammeditalk.medicationproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val atpnQesitm: String? = null,
    val atpnWarnQesitm: String? = null,
    val bizrno: String? = null,
    val depositMethodQesitm: String? = null,
    val efcyQesitm: String? = null,
    val entpName: String? = null,
    val intrcQesitm: String? = null,
    val itemImage: String? = null,
    val itemName: String? = null,
    val itemSeq: String? = null,
    val openDe: String? = null,
    val seQesitm: String? = null,
    val updateDe: String? = null,
    val useMethodQesitm: String? = null,
)
