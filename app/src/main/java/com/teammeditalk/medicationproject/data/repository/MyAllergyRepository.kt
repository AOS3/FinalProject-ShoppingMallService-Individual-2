package com.teammeditalk.medicationproject.data.repository

import androidx.datastore.core.DataStore
import com.teammeditalk.medicationproject.UserHealthInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyAllergyRepository(
    private val dataStore: DataStore<UserHealthInfo>,
) {
    val allergyFlow: Flow<List<String>> =
        dataStore.data.map {
            it.allergyInfoList
        }

    suspend fun saveAllergyInfo(allergyList: Set<String>) {
        dataStore.updateData {
            it
                .toBuilder()
                .clearAllergyInfo()
                .addAllAllergyInfo(allergyList)
                .build()
        }
    }
}
