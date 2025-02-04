package com.teammeditalk.medicationproject.ui.search.disease

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teammeditalk.medicationproject.ui.mypage.MyPageScreen
import com.teammeditalk.medicationproject.ui.mypage.MyPageViewModel
import com.teammeditalk.medicationproject.ui.util.SearchResultItem

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchMyDiseaseScreen(
    savedDiseaseList: List<String>,
    navController: NavController,
    modifier: Modifier,
    viewmodel: MyPageViewModel,
) {
    var selectedDisease by remember { mutableStateOf(savedDiseaseList) }

    val searchQuery by viewmodel.searchQuery.collectAsState()
    val searchResults by viewmodel.searchResult.collectAsState()

    Column(
        modifier =
            modifier
                .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // 1. 검색바
        Column {
            SearchBar(
                modifier = Modifier.weight(1f),
                leadingIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(MyPageScreen.Start.name)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                trailingIcon =
                    {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                        )
                    },
                query = searchQuery,
                onQueryChange = { query ->
                    viewmodel.reset()
                    viewmodel.updateSearchQuery(query)
                },
                onSearch = {
                    viewmodel.searchDiseaseName(it)
                },
                active = true,
                onActiveChange = {},
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(
                        count = searchResults.size,
                        key = { index ->
                            searchResults[index].toString()
                        },
                    ) { index ->
                        val result = searchResults[index]
                        if (result != null) {
                            SearchResultItem(
                                result,
                                onClick = {
                                    if (!savedDiseaseList.contains(result)) {
                                        selectedDisease = selectedDisease + result
                                        viewmodel.reset()
                                    }
                                },
                            )
                        }
                    }
                }
            }
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "선택된 질병",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    selectedDisease.forEach {
                        DiseaseItem(
                            diseaseName = it,
                            onDelete = {
                                selectedDisease = selectedDisease - it
                            },
                        )
                    }
                }
            }
            Button(
                onClick = {
                    viewmodel.saveDiseaseInfo(diseaseList = selectedDisease)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("저장")
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun DiseaseItem(
    onDelete: () -> Unit,
    diseaseName: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            textAlign = TextAlign.Start,
            text = diseaseName,
        )
        IconButton(
            onClick = {
                onDelete()
            },
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete button",
            )
        }
    }
}
