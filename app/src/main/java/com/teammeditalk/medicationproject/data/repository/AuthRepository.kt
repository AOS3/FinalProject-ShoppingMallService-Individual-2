package com.teammeditalk.medicationproject.data.repository

import androidx.datastore.core.DataStore
import com.teammeditalk.medicationproject.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val dataStore: DataStore<UserInfo>,
) {
    val uuid: Flow<String> =
        dataStore.data.map {
            it.uuid
        }

    // 유저 아이디 저장하기
    suspend fun saveUuid(uuid: String) {
        dataStore.updateData {
            it
                .toBuilder()
                .setUuid(uuid)
                .build()
        }
    }
}
