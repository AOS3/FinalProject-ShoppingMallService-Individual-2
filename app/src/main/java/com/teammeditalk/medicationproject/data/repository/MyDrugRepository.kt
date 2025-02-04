package com.teammeditalk.medicationproject.data.repository

import androidx.datastore.core.DataStore
import com.teammeditalk.medicationproject.UserHealthInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyDrugRepository(
    private val dataStore: DataStore<UserHealthInfo>,
) {
    val drugFlow: Flow<List<String>> =
        dataStore.data.map {
            it.drugInfoList
        }

    suspend fun saveDrugInfo(drugList: Set<String>) {
        dataStore.updateData {
            it
                .toBuilder()
                .clearDrugInfo()
                .addAllDrugInfo(drugList)
                .build()
        }
    }
}
