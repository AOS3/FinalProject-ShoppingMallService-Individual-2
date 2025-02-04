package com.teammeditalk.medicationproject.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicationproject.data.model.Item
import com.teammeditalk.medicationproject.ui.home.HomeScreen

@Suppress("ktlint:standard:function-naming")
@Composable
fun DrugDetailInfoScreen(
    navController: NavController,
    modifier: Modifier,
    drugInfo: Item,
    viewModel: DetailViewModel,
) {
    val db = Firebase.firestore
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
        TopAppBar(
            backgroundColor = Color.White,
            navigationIcon =
                {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로 가기",
                        )
                    }
                },
            title = {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    text = drugInfo.itemName.toString(),
                )
            },
        )
        AsyncImage(
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
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

        Button(
            modifier = modifier.align(Alignment.CenterHorizontally),
            onClick = {
                // todo : 장바구니에 약 담기 이후 애니메이션 추가하기
                viewModel.saveDrugIntoCart(db)
                navController.navigate(HomeScreen.Cart.name)
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
