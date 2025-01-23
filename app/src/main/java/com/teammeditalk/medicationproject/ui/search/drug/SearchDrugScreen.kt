package com.teammeditalk.medicationproject.ui.search.drug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teammeditalk.medicationproject.ui.home.HomeScreen
import com.teammeditalk.medicationproject.ui.util.SearchResultItem

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDrugScreen(
    navController: NavController,
    modifier: Modifier,
    viewmodel: SearchDrugViewModel,
) {
    val searchQuery by viewmodel.searchQuery.collectAsState()
    val searchResults by viewmodel.searchResult.collectAsState()

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // 1. 검색바
        Column {
            SearchBar(
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
                    viewmodel.searchDrugInfo(it)
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
                                    viewmodel.searchDrugDetail(result)
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
