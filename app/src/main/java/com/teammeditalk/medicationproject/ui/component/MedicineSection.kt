package com.teammeditalk.medicationproject.ui.component

import androidx.compose.foundation.layout.Arrangement
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

fun LazyListScope.medicineSection(navController: NavController) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                color = Color.Gray,
                text = "복용 중인 약물",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 16.dp),
            )
            TextButton(onClick = {
                navController.navigate(MyPageScreen.Drug.name)
            }) {
                Text(
                    "편집",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        Row {
            Chip("피부과 약")
        }
    }
}
