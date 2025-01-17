package com.teammeditalk.medicationproject.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teammeditalk.medicationproject.ui.util.SearchScreen
import com.teammeditalk.medicationproject.ui.home.HomeActivity
import com.teammeditalk.medicationproject.ui.home.HomeScreen
import com.teammeditalk.medicationproject.ui.mypage.MyPageActivity
import com.teammeditalk.medicationproject.ui.theme.MedicationProjectTheme

class SearchMyIllnessActivity : ComponentActivity(){
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MedicationProjectTheme {
                Scaffold(
topBar = {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                val intent = Intent(this@SearchMyIllnessActivity, MyPageActivity::class.java)
                startActivity(intent)
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = Color.Black
        ),
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = "약안심"
            )
        },

        )
}
                ) {innerPadding->
             SearchMyIllnessScreen(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }


}


@Composable
fun SearchMyIllnessScreen(modifier: Modifier){
    Column {
        SearchScreen(
            onSearchClick = {},
            onQueryChanged = {},
            searchResults = listOf("빈혈", "아토피피부염"),
            modifier = modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MedicationProjectTheme {
        SearchMyIllnessScreen(
            modifier = Modifier
        )

    }
}