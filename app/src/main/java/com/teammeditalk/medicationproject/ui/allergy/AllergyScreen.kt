package com.teammeditalk.medicationproject.ui.allergy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teammeditalk.medicationproject.ui.mypage.MyPageViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun AllergyScreen(
    navController: NavController,
    savedList: List<String>,
    viewModel: MyPageViewModel,
    modifier: Modifier = Modifier,
) {
    var selectedAllergies by remember { mutableStateOf(savedList.toSet()) }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(10.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            ) {
                Text(
                    text = "알레르기 편집",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }

        Text(
            modifier = modifier.padding(10.dp),
            text = "보유하고 있는 알레르기 종류를 선택해주세요",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(
            modifier = modifier.height(10.dp),
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val allergyItems =
                listOf(
                    "유제품",
                    "갑각류",
                    "육류",
                    "과일류",
                    "조개류",
                    "견과류",
                    "먼지",
                    "곰팡이",
                    "동물털",
                    "찬바람",
                    "진통제",
                    "소염제",
                    "항균제",
                    "항생제",
                    "항경련제",
                )

            items(allergyItems) {
                OutlinedButton(
                    modifier =
                        modifier
                            .width(30.dp)
                            .padding(vertical = 10.dp),
                    onClick = {
                        selectedAllergies =
                            if (it in selectedAllergies) {
                                selectedAllergies - it
                            } else {
                                selectedAllergies + it
                            }
                    },
                    colors =
                        ButtonDefaults.outlinedButtonColors(
                            containerColor =
                                if (it in selectedAllergies) {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                } else {
                                    MaterialTheme.colorScheme.surface
                                },
                        ),
                ) {
                    Text(it)
                }
            }
        }

        Spacer(
            modifier = modifier.padding(vertical = 30.dp),
        )

        Button(
            onClick = {
                viewModel.setAllergyInfo(allergyList = selectedAllergies.toList())
                navController.popBackStack()
            },
        ) {
            Text(
                modifier =
                    modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "저장",
            )
        }
    }
}
