package com.teammeditalk.medicationproject.ui.drug

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Suppress("ktlint:standard:function-naming")
@Composable
fun SetDrugScreen(modifier: Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {}) {
            androidx.compose.material.Text(
                text = "추가",
            )
        }
    }
}
