package com.teammeditalk.medicationproject.ui.search.symptom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teammeditalk.medicationproject.ui.home.HomeScreen
import com.teammeditalk.medicationproject.ui.util.SearchResultItem

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDrugBySymptomScreen(
    navController: NavController,
    modifier: Modifier,
    viewmodel: SearchDrugBySymptomViewModel,
) {
    val searchQuery by viewmodel.searchQuery.collectAsState()
    val searchResults by viewmodel.searchResult.collectAsState()

    Column(
        modifier =
            modifier
                .background(MaterialTheme.colorScheme.background),
    ) {
        // 1. 검색바
        Column {
            SearchBar(
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
                    viewmodel.searchDrugBySymptom(it)
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
                                    viewmodel.searchDrugBySymptom(symptom = result)
                                    navController.navigate(HomeScreen.Detail.name)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
