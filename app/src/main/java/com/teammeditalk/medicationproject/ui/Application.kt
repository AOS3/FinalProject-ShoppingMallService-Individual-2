package com.teammeditalk.medicationproject.ui

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.teammeditalk.medicationproject.UserHealthInfo
import com.teammeditalk.medicationproject.data.serializer.UserHealthInfoSerializer

private const val USER_HEALTH_INFO_NAME = "user_health_info"
private const val DATA_STORE_FILE_NAME = "user_health_info.pb"
private const val SORT_ORDER_KEY = "sort_order"

val Context.userHealthInfoStore: DataStore<UserHealthInfo> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = UserHealthInfoSerializer,
)

class Application : Application() {
    lateinit var userHealthdataStore: DataStore<UserHealthInfo>
        private set // 외부에서 직접 수정은 못하도록 보호

    override fun onCreate() {
        super.onCreate()
        userHealthdataStore = this.userHealthInfoStore
    }
}
