package com.teammeditalk.medicationproject.ui.mypage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teammeditalk.medicationproject.ui.theme.MedicationProjectTheme

class MyPageActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicationProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyPageScreen(
                        modifier = Modifier.padding(innerPadding),
                        onBackButtonClick = {})
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(modifier: Modifier = Modifier,
                 onBackButtonClick : () -> Unit) {
    Column(
        modifier= modifier.padding(12.dp),
       horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            ),
            title = {
                Text(
                    text = "내 정보",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp )) {
            healthInfoSection(modifier)
            allergySection()
            diseaseSection()
            medicineSection()
        }



    }

}

private fun LazyListScope.healthInfoSection(modifier: Modifier){
    item {
        InfoRow(modifier,"연령","25세")
        InfoRow(modifier,"체중","45kg")
        InfoRow(modifier,"마이 약국","")
        InfoRow(modifier,"수면 시간","")
        InfoRow(modifier,"걸음 수","")

    }
}
private fun LazyListScope.allergySection() {
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
            TextButton(onClick = {}) {
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

private fun LazyListScope.diseaseSection() {
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
            TextButton(onClick = {}) {
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

private fun LazyListScope.medicineSection() {
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
            TextButton(onClick = {}) {
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MedicationProjectTheme {
        MyPageScreen(
            onBackButtonClick = {})
    }
}