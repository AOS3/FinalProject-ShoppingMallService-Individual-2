package com.teammeditalk.medicationproject.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teammeditalk.medicationproject.ui.mypage.MyPageScreen

@OptIn(ExperimentalLayoutApi::class)
fun LazyListScope.allergySection(
    navController: NavController,
    allergyList: List<String>,
) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                color = Color.Gray,
                text = "알레르기",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 16.dp),
            )
            TextButton(
                onClick = {
                    navController.navigate(MyPageScreen.Allergy.name)
                },
            ) {
                Text(
                    "편집",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        FlowRow(
            horizontalArrangement = Arrangement.Start,
            maxItemsInEachRow = Int.MAX_VALUE,
            modifier = Modifier.fillMaxWidth(),
        ) {
            allergyList.forEach {
                Chip(it)
            }
        }
    }
}
