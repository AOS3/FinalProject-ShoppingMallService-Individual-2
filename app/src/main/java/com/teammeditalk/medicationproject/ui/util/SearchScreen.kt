package com.teammeditalk.medicationproject.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    searchResults: List<String>,
    onSearchClick : (String) -> Unit,
    onQueryChanged : (String) -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1. 검색바
        SearchBar(
            query = "빈혈",
            onQueryChange = onQueryChanged,
            onSearch = onSearchClick,
            active = true,
            onActiveChange = {},
            content ={
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        count = searchResults.size,
                        key = { index -> searchResults[index] },
                        itemContent = { index ->
                            val result = searchResults[index]
                            SearchResultItem(result)
                        }
                    )
                }
            }
        )
    }
}


@Composable
fun SearchResultItem(
    item : String,
){
    Column{
        TextButton(onClick = {}) {
            androidx.compose.material.Text(
                text = item
            )
        }

    }
}
