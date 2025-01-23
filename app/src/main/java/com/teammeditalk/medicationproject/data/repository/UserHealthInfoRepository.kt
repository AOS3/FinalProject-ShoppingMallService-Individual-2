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
    val allergyFlow: Flow<List<String>> =
        dataStore.data.map {
            it.allergyInfoList
        }

    val diseaseFlow =
        dataStore.data.map {
            it.diseaseInfoList
        }
    val drugFlow =
        dataStore.data.map {
            it.drugInfoList
        }

    val pharmacyFlow =
        dataStore.data.map {
            it.pharmacyLocation
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

    suspend fun updateAllergyInfo(allergyList: Set<String>) {
        dataStore.updateData {
            it
                .toBuilder()
                .clearAllergyInfo()
                .addAllAllergyInfo(allergyList)
                .build()
        }
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
