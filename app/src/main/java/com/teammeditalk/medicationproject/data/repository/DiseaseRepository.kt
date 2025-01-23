package com.teammeditalk.medicationproject.data.repository

import com.teammeditalk.medicationproject.data.network.ApiClient

class DiseaseRepository {
    suspend fun searchDiseaseName(searchText: String): List<String> =
        try {
            ApiClient.searchDiseaseName(searchText)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
}
