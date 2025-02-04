package com.teammeditalk.medicationproject.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

// CustomAlertDialogState.kt
data class CustomOrderDialogState(
    val allergyList: List<String> = emptyList(),
    val diseaseList: List<String> = emptyList(),
    val drugList: List<String> = emptyList(),
    val onClickConfirm: () -> Unit = {},
    val onClickCancel: () -> Unit = {},
)

@OptIn(ExperimentalLayoutApi::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun CustomOrderDialog(
    allergyList: List<String>,
    diseaseList: List<String>,
    drugList: List<String>,
) {
    var textValue by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {},
        properties =
            DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
    ) {
        Card(
            modifier =
                Modifier
                    .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    text = "\uD83D\uDE4C 주문 요청 전 확인 사항 ",
                    modifier = Modifier.padding(12.dp), // 타이틀 아래 여백 추가
                )
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "약사님께서 성현님의 건강 정보를 상세히 검토하신후 성현님께 적합한 약을 처방해주실 예정이에요!",
                    modifier = Modifier.padding(12.dp), // 타이틀 아래 여백 추가
                )

                Divider(
                    thickness = 1.dp,
                )
                Text("성현님의 건강 정보")

                Row(Modifier.padding(8.dp)) {
                    Text(text = "알레르기 : ")
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

                Row(Modifier.padding(8.dp)) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = "지병 : ",
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.Start,
                        maxItemsInEachRow = Int.MAX_VALUE,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        diseaseList.forEach {
                            Chip(it)
                        }
                    }
                }

                Row(Modifier.padding(8.dp)) {
                    Text(text = "복용 중인 약물 : ")
                    FlowRow(
                        horizontalArrangement = Arrangement.Start,
                        maxItemsInEachRow = Int.MAX_VALUE,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        drugList.forEach {
                            Chip(it)
                        }
                    }
                }
                Divider(
                    thickness = 1.dp,
                )
                TextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    label = { Text("약사님께 전달하고 싶은 내용을 입력해주세요.\n자세히 작성할수록 더욱 내 몸에 맞는 처방이 가능해요!") },
                    modifier =
                        Modifier
                            .padding(bottom = 10.dp) // 텍스트필드 아래 여백 추가
                            .fillMaxWidth()
                            .size(200.dp),
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp), // 버튼 사이 간격 추가
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                    ) {
                        Text("주문 취소하기")
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                    ) {
                        Text(text = "바로 주문하기")
                    }
                }
            }
        }
    }
}
