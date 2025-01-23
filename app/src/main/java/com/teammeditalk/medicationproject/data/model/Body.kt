package com.teammeditalk.medicationproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Body(
    val items: List<Item>,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int,
)
