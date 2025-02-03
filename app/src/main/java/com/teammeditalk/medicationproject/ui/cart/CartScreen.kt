package com.teammeditalk.medicationproject.ui.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage

data class Drug(
    val drugName: String,
    val drugCount: Int,
    val drugImageUri: String,
)

@Suppress("ktlint:standard:function-naming")
@Composable
fun CartScreen(navController: NavController) {
    Column(
        Modifier.background(Color.White),
    ) {
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                Text(
                    textAlign = TextAlign.Center,
                    text = "장바구니",
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                    )
                }
            },
        )
        Row(
            modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.Absolute.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = {},
            )
            Text(
                text = "전체 선택(2/3)",
            )
        }
        // todo : 장바구니에 아이템 추가하기
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
        ) {
            DrugItem(
                Drug("이지엔", 1, "https://nedrug.mfds.go.kr/pbp/cmn/itemImageDownload/1Orz9gcUHnw"),
            )
            DrugItem(
                Drug("타이레놀", 1, "https://nedrug.mfds.go.kr/pbp/cmn/itemImageDownload/1Orz9gcUHnw"),
            )

            DrugItem(
                Drug("까스활명수", 1, "https://nedrug.mfds.go.kr/pbp/cmn/itemImageDownload/1Orz9gcUHnw"),
            )
        }

        Button(
            modifier =
                Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
            onClick = {},
        ) {
            Text(text = "제휴 약국에 주문 요청하기")
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun DrugItem(drugItem: Drug) {
    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = { },
            )
            AsyncImage(
                modifier =
                    Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                model = drugItem.drugImageUri,
                contentDescription = null,
            )

            Column {
                Text(drugItem.drugName)
                Text("수량 : ${drugItem.drugCount}")
            }
            Button(onClick = {}) {
                Text(text = "삭제")
            }
        }
    }
}
