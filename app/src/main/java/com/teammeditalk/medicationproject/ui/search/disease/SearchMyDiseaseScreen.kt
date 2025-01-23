package com.teammeditalk.medicationproject.ui.search.disease

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teammeditalk.medicationproject.ui.mypage.MyPageViewModel
import com.teammeditalk.medicationproject.ui.util.SearchResultItem

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchMyDiseaseScreen(
    navController: NavController,
    context: Context,
    modifier: Modifier,
    viewmodel: MyPageViewModel,
) {
    val diseases by viewmodel.diseaseState.collectAsState()

    val selectedDiseaseList = remember { mutableStateListOf<String>().apply { 
        addAll(diseases)
    } }
    val searchQuery by viewmodel.searchQuery.collectAsState()
    val searchResults by viewmodel.searchResult.collectAsState()

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // 1. 검색바
        Column(
        ) {
            SearchBar(
                modifier = Modifier.height(500.dp),
                // 검색 영역이 남은 공간을 채우도록
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
                                    if (!selectedDiseaseList.contains(result)) {
                                        selectedDiseaseList.add(result)
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
                    selectedDiseaseList.forEach {
                        DiseaseItem(
                            onDelete = {
                                selectedDiseaseList.remove(it)
                            },
                            diseaseName = it,
                        )
                    }
                }
            }
            Button(
                onClick = {
                    viewmodel.saveDiseaseInfo(diseaseList = selectedDiseaseList)
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
