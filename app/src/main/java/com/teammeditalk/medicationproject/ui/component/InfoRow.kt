package com.teammeditalk.medicationproject.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Suppress("ktlint:standard:function-naming")
@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    titleText: String,
    contentValue: String,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            color = Color.Gray,
            textAlign = TextAlign.Left,
            text = titleText,
        )

        Text(
            fontSize = 18.sp,
            text = contentValue,
            color = Color.Black,
        )
        TextButton(onClick = {}) {
            Text(
                "편집",
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
