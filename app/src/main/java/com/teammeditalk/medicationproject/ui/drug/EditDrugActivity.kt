package com.teammeditalk.medicationproject.ui.drug

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teammeditalk.medicationproject.ui.mypage.MyPageActivity
import com.teammeditalk.medicationproject.ui.search.SearchScreen
import com.teammeditalk.medicationproject.ui.theme.MedicationProjectTheme

class EditDrugActivity : ComponentActivity() {

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
                                    val intent =
                                        Intent(this@EditDrugActivity, MyPageActivity::class.java)
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
                    }) { innerPadding ->
                    EditTakingDrugScreen(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun EditTakingDrugScreen(modifier: Modifier){
    Column(modifier = Modifier.fillMaxSize()){

        com.teammeditalk.medicationproject.ui.util.SearchScreen(
            onSearchClick = {},
            onQueryChanged = {},
            searchResults = listOf("훼스탈", "타이레놀"),
            modifier = modifier
        )
        Button(onClick = {}) {
            androidx.compose.material.Text(
                text = "추가"
            )
        }

    }
}