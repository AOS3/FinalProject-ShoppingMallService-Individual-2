package com.teammeditalk.medicationproject.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.teammeditalk.medicationproject.data.model.Item
import com.teammeditalk.medicationproject.ui.component.TopAppBar

@Suppress("ktlint:standard:function-naming")
@Composable
fun DrugDetailScreen(
    modifier: Modifier,
    drugInfo: Item,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier =
            modifier
                .verticalScroll(scrollState)
                .padding(5.dp),
    ) {
        TopAppBar(
            "약 상세 정보",
            context = LocalContext.current,
        )
        Text(
            modifier = modifier.padding(bottom = 10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            text = drugInfo.itemName.toString(),
        )
        AsyncImage(
            model = drugInfo.itemImage.toString(),
            contentDescription = null,
        )

        DrugInfoItem(
            modifier = modifier,
            title = "효능 : ",
            info = drugInfo.efcyQesitm.toString(),
        )

        DrugInfoItem(
            modifier = modifier,
            title = "먹기 전 알아야할 내용 : ",
            info = drugInfo.atpnWarnQesitm.toString(),
        )
        DrugInfoItem(
            modifier = modifier,
            title = "주의 사항 : ",
            info = drugInfo.atpnWarnQesitm.toString(),
        )
        DrugInfoItem(
            modifier = modifier,
            title = "주의해야할 음식 또는 약 : ",
            info = drugInfo.intrcQesitm.toString(),
        )
        DrugInfoItem(
            modifier = modifier,
            title = "이상 반응 : ",
            info = drugInfo.seQesitm.toString(),
        )

        Button(
            modifier = modifier.align(Alignment.CenterHorizontally),
            onClick = {},
        ) {
            Text(
                text = "장바구니에 담기",
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun DrugInfoItem(
    modifier: Modifier,
    title: String,
    info: String,
) {
    Row(
        modifier = modifier.padding(5.dp),
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = title,
            textAlign = TextAlign.Start,
        )
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = info,
        )
    }
}
