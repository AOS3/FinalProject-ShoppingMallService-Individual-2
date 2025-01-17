package com.teammeditalk.medicationproject.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.lifecycleScope
import com.teammeditalk.medicationproject.R
import com.teammeditalk.medicationproject.ui.drug.EditDrugActivity
import com.teammeditalk.medicationproject.ui.edit.EditAllergyActivity
import com.teammeditalk.medicationproject.ui.home.HomeActivity
import com.teammeditalk.medicationproject.ui.search.SearchMyIllnessActivity
import com.teammeditalk.medicationproject.ui.theme.MedicationProjectTheme
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

class MyPageActivity : ComponentActivity() {

    var sleepDuration by mutableStateOf("--")
    var stepCount by mutableStateOf(0)
    val healthConnectClient by lazy { HealthConnectClient.getOrCreate(this) }
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private val permissions = setOf(
        HealthPermission.getReadPermission(WeightRecord::class),
        HealthPermission.getReadPermission(SleepSessionRecord::class),
       HealthPermission.getReadPermission(StepsRecord::class),
        )




    // 권한 요청을 위한 launcher 설정
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private val requestPermission = registerForActivityResult(
        PermissionController.createRequestPermissionResultContract()
    ) {
        if(it.containsAll(permissions)){
            lifecycleScope.launch {
                Log.d("권한","권한 허용")
            }
        }
        else {
            Log.d("권한","권한 거부")
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private suspend fun checkPermissionsAndRequest(){
        val granted = healthConnectClient.permissionController.getGrantedPermissions().containsAll(permissions)
        if (granted){
            readWeightInputs(healthConnectClient)
            sleepDuration = readSleepData(healthConnectClient)
            stepCount = readStepByTimeRange(healthConnectClient)
        }
        else {
            requestPermission.launch(permissions)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicationProjectTheme {
                Scaffold(
                    topBar = {    CenterAlignedTopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = Color.Black
                        ),
                        title = {
                            Text(
                                textAlign = TextAlign.Center,
                                text = "내 정보",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                val intent = Intent(this@MyPageActivity, HomeActivity::class.java)
                                startActivity(intent)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowLeft,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    )},
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyPageScreen(
                        context = this@MyPageActivity,
                        modifier = Modifier.padding(innerPadding),
                        onBackButtonClick = {
                            val intent = Intent(this@MyPageActivity, HomeActivity::class.java)
                            startActivity(intent)
                        },
                        sleepDuration = sleepDuration,
                        stepCount = stepCount,
                        )
                }
            }


            LaunchedEffect(Unit) {
                checkPermissionsAndRequest()
            }

        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    context : Context,
    modifier: Modifier = Modifier,
                 onBackButtonClick : () -> Unit,
                 sleepDuration : String,
                 stepCount : Int
                 ) {

    Column(
       horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp )) {
            healthInfoSection(modifier,sleepDuration,stepCount)
            allergySection(context)
            diseaseSection(context)
            medicineSection(context)
        }


    }

}

private fun LazyListScope.healthInfoSection(modifier: Modifier,sleepDuration: String,stepCount : Int){
    item {
        InfoRow(modifier,"연령","25세")
        InfoRow(modifier,"체중","45kg")
        InfoRow(modifier,"마이 약국","")
        InfoRow(modifier,"수면 시간",sleepDuration)
        InfoRow(modifier,"걸음 수",stepCount.toString()+"걸음")

    }
}
private fun LazyListScope.allergySection(context: Context) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Text(
                color = Color.Gray,
                text = "알레르기",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TextButton(onClick =
            {
                val intent = Intent(context,EditAllergyActivity::class.java)
                context.startActivity(intent)
            }
            ) {
                Text(
                    "편집",
                    color = MaterialTheme.colorScheme.primary
                )
            }


        }
        Row {
            Chip("견과류")
            Chip("꽃가루")
        }
    }

}

private fun LazyListScope.diseaseSection(context : Context) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Text(
                color = Color.Gray,
                text = "지병",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TextButton(onClick = {
                val intent = Intent(context, SearchMyIllnessActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(
                    "편집",
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
        Row {
            Chip("빈혈")
        }
    }
}

private fun LazyListScope.medicineSection(context : Context) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

            ){
            Text(
                color = Color.Gray,
                text = "복용 중인 약물",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TextButton(onClick = {
                val intent = Intent(context,EditDrugActivity::class.java)
                context.startActivity(intent)

            }) {
                Text(
                    "편집",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Row {
            Chip("피부과 약")
        }
    }
}

@Composable
fun Chip(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp,MaterialTheme.colorScheme.outline),
        modifier = modifier.padding(end=8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodyMedium

        )
    }
}
@Composable
private fun InfoRow(
    modifier: Modifier = Modifier,
    titleText : String,
    contentValue : String,
    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            color = Color.Gray,
            textAlign = TextAlign.Left,
            text = titleText
        )

        Text(
            fontSize = 18.sp,
            text = contentValue,
            color = Color.Black
        )
        TextButton(onClick = {}) {
            Text(
                "편집",
                color = MaterialTheme.colorScheme.primary
            )
        }


    }
}

private suspend fun readWeightInputs(healthConnectClient: HealthConnectClient): List<WeightRecord>? {
    try {
        val endTime = Instant.now()
        val startTime = endTime.minus(30, ChronoUnit.DAYS)
        val request = ReadRecordsRequest(
            recordType = WeightRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
        )
        val response = healthConnectClient.readRecords(request)
        return response.records
    } catch (e: Exception) {
        Log.d("failed to read weight", e.toString())
        return null
    }

}


@RequiresApi(Build.VERSION_CODES.S)
private suspend fun readSleepData(healthConnectClient: HealthConnectClient) :String{
    try {
        val endTime = Instant.now()
        val startTime = endTime.minus(1, ChronoUnit.DAYS)
        val request = ReadRecordsRequest(
            recordType = SleepSessionRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
        )
        val response = healthConnectClient.readRecords(request)
        return if (response.records.isNotEmpty()) {
            val lastSession = response.records.maxBy { it.startTime }
            val duration = Duration.between(
                lastSession.startTime,
                lastSession.endTime
            )
            String.format(
                "%d시간 %d분",
                duration.toHours(),
                duration.toMinutesPart()
            )
        } else {
            "--"
        }
    }catch (e:Exception){
        Log.e("수면 시간 데이터", "수면 데이터 읽기 실패", e)
        return "--"
    }



}

private suspend fun readStepByTimeRange(healthConnectClient: HealthConnectClient) : Int {
    val endTime = Instant.now()
    val startTime = endTime.minus(30, ChronoUnit.DAYS)
    try {
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
        if (response.records.isNotEmpty()) {
            val lastSession = response.records.maxBy { it.startTime }
            Log.d("걸음 수 데이터", "${lastSession.count}")
            return lastSession.count.toInt()
        }
        else {
            return 0
        }
    } catch (e: Exception) {
        Log.e("걸음수 데이터", "걸음 데이터 읽기 실패", e)
        return 0
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MedicationProjectTheme {
        MyPageScreen(
            context = LocalContext.current,
            onBackButtonClick = {} ,
            sleepDuration = "7시간 30분",
            stepCount = 13,
        )
    }
}

