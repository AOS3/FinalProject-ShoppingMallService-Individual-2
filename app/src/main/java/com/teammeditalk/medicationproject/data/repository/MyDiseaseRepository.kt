package com.teammeditalk.medicationproject.data.repository

import androidx.datastore.core.DataStore
import com.teammeditalk.medicationproject.UserHealthInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyDiseaseRepository(
    private val dataStore: DataStore<UserHealthInfo>,
) {
    val diseaseFlow: Flow<List<String>> =
        dataStore.data.map {
            it.diseaseInfoList
        }

    suspend fun saveDiseaseInfo(diseaseList: List<String>) {
        dataStore.updateData {
            it
                .toBuilder()
                .clearDiseaseInfo()
                .addAllDiseaseInfo(diseaseList)
                .build()
        }
    }
}
