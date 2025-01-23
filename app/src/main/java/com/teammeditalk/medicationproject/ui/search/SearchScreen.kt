package com.teammeditalk.medicationproject.ui.search

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teammeditalk.medicationproject.ui.mypage.MyPageActivity
import com.teammeditalk.medicationproject.ui.mypage.MyPageViewModel
import com.teammeditalk.medicationproject.ui.util.SearchResultItem

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    context: Context,
    modifier: Modifier,
    viewmodel: MyPageViewModel,
) {
    val searchQuery by viewmodel.searchQuery.collectAsState()
    val searchResults by viewmodel.searchResult.collectAsState()

    Column(
        modifier =
            modifier
                .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // 1. 검색바
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
                viewmodel.searchDiseaseName(it)
            },
            active = true,
            onActiveChange = {},
            content = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(
                        count = searchResults.size,
                        key = { index -> searchResults[index].toString() },
                        itemContent = { index ->
                            val result = searchResults[index]
                            if (result != null) {
                                SearchResultItem(
                                    result,
                                    onClick = {
                                        val intent =
                                            Intent(context, MyPageActivity::class.java).apply {
                                                putExtra("selected_disease", result)
                                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                            }
                                        context.startActivity(intent)
                                    },
                                )
                            }
                        },
                    )
                }
            },
        )
    }
}
