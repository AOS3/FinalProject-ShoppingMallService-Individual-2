package com.teammeditalk.medicationproject.ui.mypage

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicationproject.data.repository.DiseaseRepository
import com.teammeditalk.medicationproject.data.repository.MyAllergyRepository
import com.teammeditalk.medicationproject.data.repository.MyDiseaseRepository
import com.teammeditalk.medicationproject.data.repository.MyDrugRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

// todo
// MyPageActivity에서 네비게이션을 통해 이동하는 화면 관련 코드가 다 모인다
// 개선 필요 !
class MyPageViewModel(
    private val myAllergyRepository: MyAllergyRepository,
    private val diseaseRepository: DiseaseRepository,
    private val myDiseaseRepository: MyDiseaseRepository,
    private val myDrugRepository: MyDrugRepository,
) : ViewModel() {
    val diseaseFLow = myDiseaseRepository.diseaseFlow
    val allergyFlow = myAllergyRepository.allergyFlow

    init {
        collectFlows()
    }

    private fun collectFlows() {
        viewModelScope.launch {
            myDiseaseRepository.diseaseFlow
                .catch { error ->
                    Log.e("Disease Flow", "Error collecting disease data", error)
                    _diseaseState.value = emptyList()
                }.collect { diseases ->
                    Log.d("Disease Flow", "Received diseases: $diseases")
                    _diseaseState.value = diseases
                }
        }

        viewModelScope.launch {
            myAllergyRepository.allergyFlow
                .catch { error ->
                    Log.e("Allergy Flow", "Error collecting allergy data", error)
                    _allergyState.value = emptyList()
                }.collect { allergies ->
                    Log.d("Allergy Flow", "Received allergies: $allergies")
                    _allergyState.value = allergies
                }
        }
    }

    private val _diseaseState = MutableStateFlow(emptyList<String>())
    val diseaseState = _diseaseState.asStateFlow()

    private val _allergyState = MutableStateFlow(emptyList<String>())
    val allergyState = _allergyState.asStateFlow()

    private val _searchQuery =
        MutableStateFlow("")
    val searchQuery =
        _searchQuery.asStateFlow()

    private val _searchResult =
        MutableStateFlow(emptyList<String?>())

    val searchResult = _searchResult.asStateFlow()

    // 몸무게 데이터 가져오기
    private suspend fun readWeightInputs(healthConnectClient: HealthConnectClient): List<WeightRecord>? {
        try {
            val endTime = Instant.now()
            val startTime = endTime.minus(30, ChronoUnit.DAYS)
            val request =
                ReadRecordsRequest(
                    recordType = WeightRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
                )
            val response = healthConnectClient.readRecords(request)
            return response.records
        } catch (e: Exception) {
            Log.d("failed to read weight", e.toString())
            return null
        }
    }

    // 헬스 커넥트 데이터 가져오기
    @RequiresApi(Build.VERSION_CODES.S)
    fun getUserData(healthConnectClient: HealthConnectClient) {
        viewModelScope.launch {
            readWeightInputs(healthConnectClient)
            readSleepData(healthConnectClient)
            readStepByTimeRange(healthConnectClient)
        }
    }

    // 수면 데이터 가져오기
    @RequiresApi(Build.VERSION_CODES.S)
    suspend fun readSleepData(healthConnectClient: HealthConnectClient): String {
        try {
            val endTime = Instant.now()
            val startTime = endTime.minus(1, ChronoUnit.DAYS)
            val request =
                ReadRecordsRequest(
                    recordType = SleepSessionRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
                )
            val response = healthConnectClient.readRecords(request)
            return if (response.records.isNotEmpty()) {
                val lastSession = response.records.maxBy { it.startTime }
                val duration =
                    Duration.between(
                        lastSession.startTime,
                        lastSession.endTime,
                    )
                String.format(
                    "%d시간 %d분",
                    duration.toHours(),
                    duration.toMinutesPart(),
                )
            } else {
                "--"
            }
        } catch (e: Exception) {
            Log.e("수면 시간 데이터", "수면 데이터 읽기 실패", e)
            return "--"
        }
    }

    // 걸음 수 데이터 가져오기

    private suspend fun readStepByTimeRange(healthConnectClient: HealthConnectClient): Int {
        val endTime = Instant.now()
        val startTime = endTime.minus(30, ChronoUnit.DAYS)
        try {
            val response =
                healthConnectClient.readRecords(
                    ReadRecordsRequest(
                        StepsRecord::class,
                        timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
                    ),
                )
            if (response.records.isNotEmpty()) {
                val lastSession = response.records.maxBy { it.startTime }
                Log.d("걸음 수 데이터", "${lastSession.count}")
                return lastSession.count.toInt()
            } else {
                return 0
            }
        } catch (e: Exception) {
            Log.e("걸음수 데이터", "걸음 데이터 읽기 실패", e)
            return 0
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun reset() {
        _searchQuery.value = ""
        _searchResult.value = emptyList()
    }

    fun searchDiseaseName(searchText: String) {
        viewModelScope.launch {
            val result =
                diseaseRepository
                    .searchDiseaseName(searchText)
            _searchResult.value = result
        }
    }

    fun saveAllergyInfo(allergyList: List<String>) {
        viewModelScope.launch {
            myAllergyRepository.saveAllergyInfo(allergyList.toSet())
        }
    }

    fun saveDiseaseInfo(diseaseList: List<String>) {
        viewModelScope.launch {
            myDiseaseRepository.saveDiseaseInfo(diseaseList)
        }
    }
}
