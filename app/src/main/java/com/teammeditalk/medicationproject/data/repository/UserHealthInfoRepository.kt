package com.teammeditalk.medicationproject.data.repository

import androidx.datastore.core.DataStore
import com.teammeditalk.medicationproject.PharmacyLocation
import com.teammeditalk.medicationproject.UserHealthInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserHealthInfoRepository(
    private val dataStore: DataStore<UserHealthInfo>,
) {
    val healthInfoFlow: Flow<UserHealthInfo> = dataStore.data

    val drugFlow =
        dataStore.data.map {
            it.drugInfoList
        }

    val pharmacyFlow =
        dataStore.data.map {
            it.pharmacyLocation
        }

    suspend fun saveDrugInfo(drugList: List<String>) {
        dataStore.updateData {
            it
                .toBuilder()
                .clearDrugInfo()
                .addAllDrugInfo(drugList)
                .build()
        }
    }

    suspend fun savePharmacyInfo(pharmacyLocation: PharmacyLocation) {
        dataStore.updateData {
            it
                .toBuilder()
                .setPharmacyLocation(pharmacyLocation)
                .build()
        }
    }

    suspend fun saveUserHealthInfo(
        age: Int,
        weight: Double,
    ) {
        dataStore.updateData {
            it
                .toBuilder()
                .setAge(age)
                .setWeight(weight)
                .build()
        }
    }
}
