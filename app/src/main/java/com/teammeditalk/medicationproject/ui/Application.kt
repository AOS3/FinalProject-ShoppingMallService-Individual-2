package com.teammeditalk.medicationproject.ui

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.kakao.vectormap.KakaoMapSdk
import com.teammeditalk.medicationproject.Constant.KAKAO_MAP_KEY
import com.teammeditalk.medicationproject.UserHealthInfo
import com.teammeditalk.medicationproject.UserInfo
import com.teammeditalk.medicationproject.data.serializer.UserHealthInfoSerializer
import com.teammeditalk.medicationproject.data.serializer.UserInfoSerializer

private const val USER_HEALTH_INFO_NAME = "user_health_info"
private const val DATA_STORE_FILE_NAME = "user_health_info.pb"
private const val SORT_ORDER_KEY = "sort_order"

class Application : Application() {
    companion object {
        private lateinit var instance: com.teammeditalk.medicationproject.ui.Application

        fun getInstance(): com.teammeditalk.medicationproject.ui.Application = instance
    }

    private val Context.userHealthInfoStore: DataStore<UserHealthInfo> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = UserHealthInfoSerializer,
    )

    private val Context.userInfoStore: DataStore<UserInfo> by dataStore(
        fileName = "user_info_pb",
        serializer = UserInfoSerializer,
    )

    lateinit var userInfoDataStore: DataStore<UserInfo>
        private set // 외부에서 직접 수정은 못하도록 보호

    lateinit var userHealthdataStore: DataStore<UserHealthInfo>
        private set // 외부에서 직접 수정은 못하도록 보호

    override fun onCreate() {
        super.onCreate()

        instance = this
        userHealthdataStore = userHealthInfoStore
        userInfoDataStore = userInfoStore

        KakaoMapSdk.init(this, KAKAO_MAP_KEY)
    }
}
