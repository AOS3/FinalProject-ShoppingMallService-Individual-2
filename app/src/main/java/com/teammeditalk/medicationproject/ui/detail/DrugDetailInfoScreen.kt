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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.teammeditalk.medicationproject.data.model.Item

@Suppress("ktlint:standard:function-naming")
@Composable
fun DrugDetailInfoScreen(
    modifier: Modifier,
    drugInfo: Item,
    viewModel: DetailViewModel,
) {
    val myKeyWord by viewModel.myKeyWord.collectAsState()
    viewModel.setDrugInfo(drugInfo)
    viewModel.findMyIllness()
    val scrollState = rememberScrollState()
    Column(
        modifier =
            modifier
                .verticalScroll(scrollState)
                .padding(5.dp),
    ) {
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
            title = "성현님과 관련된 키워드 : ",
            info = if (myKeyWord.isEmpty()) "없음" else myKeyWord.toString(),
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
            title = "사용 시 주의 사항 : ",
            info = drugInfo.atpnQesitm.toString(),
        )
        DrugInfoItem(
            modifier = modifier,
            title = "약 복용 전 알아둬야할 사항 : ",
            info = drugInfo.atpnWarnQesitm.toString(),
        )
        DrugInfoItem(
            modifier = modifier,
            title = "주의해야 할 약 또는 음식 : ",
            info = drugInfo.intrcQesitm.toString(),
        )

//        DrugInfoItem(
//            modifier = modifier,
//            title = "이상 반응 : ",
//            info = drugInfo.seQesitm.toString(),
//        )

        Button(
            modifier = modifier.align(Alignment.CenterHorizontally),
            onClick = {
                // todo : 장바구니에 약 담기
                viewModel.saveDrugIntoCart()
            },
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
