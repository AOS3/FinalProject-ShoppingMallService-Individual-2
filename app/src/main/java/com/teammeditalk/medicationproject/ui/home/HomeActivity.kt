package com.teammeditalk.medicationproject.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teammeditalk.medicationproject.data.model.Item
import com.teammeditalk.medicationproject.data.repository.DrugRepository
import com.teammeditalk.medicationproject.ui.detail.DrugDetailScreen
import com.teammeditalk.medicationproject.ui.mypage.MyPageActivity
import com.teammeditalk.medicationproject.ui.search.drug.SearchDrugScreen
import com.teammeditalk.medicationproject.ui.search.drug.SearchDrugViewModel
import com.teammeditalk.medicationproject.ui.search.drug.SearchDrugViewModelFactory
import com.teammeditalk.medicationproject.ui.theme.MedicationProjectTheme

enum class HomeScreen {
    Home,
    Search,
    Detail,
    Cart,
}

class HomeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel =
            ViewModelProvider(
                this,
                SearchDrugViewModelFactory(DrugRepository()),
            )[SearchDrugViewModel::class.java]

        setContent {
            var drugInfo by remember { mutableStateOf(emptyList<Item>()) }

            LaunchedEffect(Unit) {
                viewModel.drugInfo.collect {
                    drugInfo = it
                }
            }
            val navController = rememberNavController()

            MedicationProjectTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            modifier = Modifier.fillMaxWidth(),
                            colors =
                                TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = Color.Black,
                                ),
                            title = {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = "약안심",
                                )
                            },
                        )
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreen.Home.name,
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                    ) {
                        composable(route = HomeScreen.Home.name) {
                            HomeScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                onSymptomItemClick = {},
                                onPartItemClick = {},
                            )
                        }
                        composable(route = HomeScreen.Search.name) {
                            SearchDrugScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                viewmodel = viewModel,
                            )
                        }
                        composable(route = HomeScreen.Detail.name) {
                            DrugDetailScreen(
                                modifier = Modifier.padding(innerPadding),
                                drugInfo = if (drugInfo.isNotEmpty()) drugInfo[0] else Item(),
                            )
                        }
                        composable(route = HomeScreen.Cart.name) {
                        }
                    }
                }
            }
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    fun HomeScreen(
        navController: NavController,
        modifier: Modifier,
        onSymptomItemClick: () -> Unit,
        onPartItemClick: () -> Unit,
    ) {
        Column(
            modifier =
                modifier
                    .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchBar(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                onClick = {
                    navController.navigate(HomeScreen.Search.name)
                },
            )
            // 증상별 검색
            CategorySection(
                title = "증상별로 검색",
                items = listOf("두통", "감기", "소화불량", "피로"),
                onItemClick = onSymptomItemClick,
            )

            // 약 이름을 검색
            CategorySection(
                title = "약 이름으로 검색",
                items = listOf("머리", "목", "가슴", "배"),
                onItemClick = onPartItemClick,
            )
            Spacer(modifier = Modifier.weight(1f))

            // 하단 네비게이션
            BottomNav()
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    private fun BottomNav(context: Context = LocalContext.current) {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(imageVector = (Icons.Default.Home), "홈") },
                label = { Text("홈") },
                selected = true,
                onClick = { /* 처리 */ },
            )
            NavigationBarItem(
                icon = { Icon(imageVector = (Icons.Default.ShoppingCart), "Saved") },
                label = { Text("장바구니") },
                selected = false,
                onClick = { /* 처리 */ },
            )
            NavigationBarItem(
                icon = { Icon(imageVector = (Icons.Default.Person), "마이페이지") },
                label = { Text("마이페이지") },
                selected = false,
                onClick = {
                    val intent = Intent(context, MyPageActivity::class.java)
                    context.startActivity(intent)
                },
            )
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    private fun SearchBar(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
    ) {
        Surface(
            modifier =
                modifier
                    .height(48.dp)
                    .clickable(onClick = onClick),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "검색",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "내 증상으로 검색",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )
            }
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    private fun CategorySection(
        title: String,
        items: List<String>,
        onItemClick: () -> Unit,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                TextButton(onClick = { /* 전체보기 */ }) {
                    Text("전체보기")
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(items) { item ->
                }
            }
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MedicationProjectTheme {
            HomeScreen(
                navController = NavController(this),
                modifier = Modifier,
                onSymptomItemClick = {},
                onPartItemClick = {},
            )
        }
    }
}
