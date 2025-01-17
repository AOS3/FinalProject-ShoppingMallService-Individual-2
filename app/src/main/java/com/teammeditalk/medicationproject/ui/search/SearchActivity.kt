package com.teammeditalk.medicationproject.ui.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teammeditalk.medicationproject.ui.home.HomeScreen
import com.teammeditalk.medicationproject.ui.theme.MedicationProjectTheme

// 이번 플젝은 테마별로 만들어보기 ( 검색, 로그인, 홈, 주문 등)
class SearchActivity  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicationProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SearchScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun SearchScreen(modifier: Modifier = Modifier){
}