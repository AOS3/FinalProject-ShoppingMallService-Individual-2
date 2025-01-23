package com.teammeditalk.medicationproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DrugResponse(
    val body: Body,
    val header: Header,
)
