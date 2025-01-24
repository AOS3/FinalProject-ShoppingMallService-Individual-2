package com.teammeditalk.medicationproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Header(
    val resultCode: String,
    val resultMsg: String,
)
