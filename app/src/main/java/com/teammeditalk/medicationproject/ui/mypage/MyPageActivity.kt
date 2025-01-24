package com.teammeditalk.medicationproject.ui.mypage

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teammeditalk.medicationproject.data.repository.DiseaseRepository
import com.teammeditalk.medicationproject.data.repository.MyAllergyRepository
import com.teammeditalk.medicationproject.data.repository.MyDiseaseRepository
import com.teammeditalk.medicationproject.ui.Application
import com.teammeditalk.medicationproject.ui.allergy.AllergyScreen
import com.teammeditalk.medicationproject.ui.component.TopAppBar
import com.teammeditalk.medicationproject.ui.component.allergySection
import com.teammeditalk.medicationproject.ui.component.diseaseSection
import com.teammeditalk.medicationproject.ui.component.healthInfoSection
import com.teammeditalk.medicationproject.ui.component.medicineSection
import com.teammeditalk.medicationproject.ui.drug.SetDrugScreen
import com.teammeditalk.medicationproject.ui.search.disease.SearchMyDiseaseScreen
import com.teammeditalk.medicationproject.ui.theme.MedicationProjectTheme
import kotlinx.coroutines.launch

enum class MyPageScreen {
    Start,
    Allergy,
    Disease,
    Drug,
}

class MyPageActivity : ComponentActivity() {
    // 지병 리스트
    private var sleepDuration by mutableStateOf("--")
    private var stepCount by mutableIntStateOf(0)

    private val dataStore by lazy {
        (application as Application).userHealthdataStore
    }

    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(this) }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private val permissions =
        setOf(
            HealthPermission.getReadPermission(WeightRecord::class),
            HealthPermission.getReadPermission(SleepSessionRecord::class),
            HealthPermission.getReadPermission(StepsRecord::class),
        )

    // 권한 요청을 위한 launcher 설정
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private val requestPermission =
        registerForActivityResult(
            PermissionController.createRequestPermissionResultContract(),
        ) {
            if (it.containsAll(permissions)) {
                lifecycleScope.launch {
                    Log.d("권한", "권한 허용")
                }
            } else {
                Log.d("권한", "권한 거부")
            }
        }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private suspend fun checkPermissionsAndRequest(viewModel: MyPageViewModel) {
        val granted = healthConnectClient.permissionController.getGrantedPermissions().containsAll(permissions)
        if (granted) {
            viewModel.getUserData(healthConnectClient)
        } else {
            requestPermission.launch(permissions)
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewmodel =
            ViewModelProvider(
                this,
                MyPageViewModelFactory(
                    DiseaseRepository(),
                    MyDiseaseRepository(dataStore = dataStore),
                    MyAllergyRepository(dataStore = dataStore),
                ),
            )[MyPageViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val savedAllergies by viewmodel.allergyState.collectAsState()
            val savedDisease by viewmodel.diseaseState.collectAsState()
            LaunchedEffect(Unit) {
                checkPermissionsAndRequest(viewmodel)
            }

            MedicationProjectTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(context = this, title = "약 안심")
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = MyPageScreen.Start.name,
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                    ) {
                        composable(route = MyPageScreen.Start.name) {
                            MyPageScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                sleepDuration = sleepDuration,
                                stepCount = stepCount,
                                allergyList = savedAllergies,
                                diseaseList = savedDisease,
                            )
                        }
                        composable(route = MyPageScreen.Allergy.name) {
                            AllergyScreen(
                                navController = navController,
                                savedList = savedAllergies,
                                modifier = Modifier,
                                viewModel = viewmodel,
                            )
                        }
                        composable(route = MyPageScreen.Disease.name) {
                            SearchMyDiseaseScreen(
                                navController = navController,
                                viewmodel = viewmodel,
                                modifier = Modifier.padding(innerPadding),
                                savedDiseaseList = savedDisease,
                            )
                        }
                        composable(route = MyPageScreen.Drug.name) {
                            SetDrugScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun MyPageScreen(
    navController: NavController,
    allergyList: List<String>,
    diseaseList: List<String>,
    modifier: Modifier = Modifier,
    sleepDuration: String,
    stepCount: Int,
) {
    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
        ) {
            healthInfoSection(modifier, sleepDuration, stepCount)
            allergySection(navController = navController, allergyList = allergyList)
            diseaseSection(navController = navController, diseaseList = diseaseList)
            medicineSection(navController)
        }
    }
}
