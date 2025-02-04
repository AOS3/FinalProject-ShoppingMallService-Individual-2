package com.teammeditalk.medicationproject.ui.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teammeditalk.medicationproject.ui.home.HomeScreen

@Suppress("ktlint:standard:function-naming")
@Composable
fun OrderCompleteScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "주문 완료",
        )
        Text(
            modifier = Modifier.padding(bottom = 7.dp),
            style = MaterialTheme.typography.h5,
            text = "처방요청 완료",
        )
        Text(
            modifier = Modifier.padding(bottom = 7.dp),
            style = MaterialTheme.typography.subtitle1,
            text = "밝은 미소 약국으로 처방 요청이 전달되었습니다.",
        )

        Text(
            style = MaterialTheme.typography.subtitle1,
            text = "☺\uFE0F성현님의 건강 정보를 검토한 후 알림을 보내드릴게요",
        )

        TextButton(
            onClick = {
                navController.navigate(HomeScreen.OrderDetail.name)
            },
        ) {
            Text(
                text = "처방 요청 상세 보기",
            )
        }
    }
}
