package com.teammeditalk.medicationproject.ui.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.teammeditalk.medicationproject.data.model.drug.DrugInCart
import com.teammeditalk.medicationproject.ui.component.CustomOrderDialog
import com.teammeditalk.medicationproject.ui.home.HomeScreen
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Suppress("ktlint:standard:function-naming")
@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navController: NavController,
) {
    val customOrderDialogState = viewModel.customOrderDialogState.value
    val drugList by viewModel.drugList.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = true)

    // 선택된 아이템을 관리하기 위함!
    var selectedDrugs by remember { mutableStateOf(listOf<DrugInCart>()) }

    // 전체 토클 상태
    var isAllSelected by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getDrugItemInCart()
    }
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
                        navController.navigate(HomeScreen.Home.name)
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
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = isAllSelected,
                    onCheckedChange = {
                        isAllSelected = it
                        selectedDrugs = if (it) drugList else emptyList()
                    },
                )
                Text(
                    // todo : 체크된 아이템의 수 적용
                    text = "전체 선택(${selectedDrugs.size}/${drugList.size})",
                )
            }

            TextButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.deleteDrugItemInCart(selectedDrugs)
                    }
                },
            ) {
                Text("선택 삭제")
            }
        }
        // todo : 장바구니에 아이템 추가하기
        if (isLoading) {
            LoadingScreen()
        } else {
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            ) {
                LazyColumn {
                    items(drugList) { drug ->
                        DrugItem(
                            drugItem = drug,
                            isSelected = drug in selectedDrugs,
                            onSelectionChange = { isSelected ->
                                selectedDrugs =
                                    if (isSelected) {
                                        selectedDrugs + drug
                                    } else {
                                        selectedDrugs - drug
                                    }
                                isAllSelected = selectedDrugs.size == drugList.size
                            },
                        )
                    }
                }
            }
        }

        Button(
            modifier =
                Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
            onClick = {
                viewModel.showCustomOrderDialog()
                viewModel.setSelectedDrugList(selectedDrugs)

                // 주문한 약은 장바구니에서 없애기
                viewModel.deleteDrugItemInCart(selectedDrugs)
            },
        ) {
            Text(text = "제휴 약국에 주문 요청하기")
        }
    }
    if (customOrderDialogState.allergyList.isNotEmpty()) {
        CustomOrderDialog(
            allergyList = customOrderDialogState.allergyList,
            diseaseList = customOrderDialogState.diseaseList,
            drugList = customOrderDialogState.drugList,
            onClickConfirm = { message ->
                customOrderDialogState.onClickConfirm(message)
            },
            onClickCancel = customOrderDialogState.onClickCancel,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun DrugItem(
    isSelected: Boolean = false,
    drugItem: DrugInCart,
    onSelectionChange: (Boolean) -> Unit,
) {
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
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = onSelectionChange,
            )
//            AsyncImage(
//                modifier =
//                    Modifier
//                        .size(50.dp)
//                        .clip(RoundedCornerShape(10.dp)),
//                model = drugItem.drugImageUri,
//                contentDescription = null,
//            )

            Column(
                Modifier.padding(5.dp),
            ) {
                Text(
                    maxLines = 2,
                    text = drugItem.drugName,
                )
                Text("수량 : ${1}")
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
// 로딩 화면 추가
@Composable
fun LoadingScreen() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(50.dp),
        )
    }
}
