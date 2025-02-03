package com.teammeditalk.medicationproject.data.repository

import com.teammeditalk.medicationproject.data.model.Item
import com.teammeditalk.medicationproject.data.network.ApiClient

class DrugRepository {
    suspend fun getDrugBySymptom(symptom: String): List<Item> =
        try {
            ApiClient.getDrugBySymptom(symptom)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    suspend fun getDrugList(itemName: String): List<String> =
        try {
            ApiClient.getDrbEasyDrugList(itemName)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    suspend fun getDrugInfo(itemName: String): List<Item> =
        try {
            ApiClient.getDrugDetailInfo(itemName = itemName)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
}
