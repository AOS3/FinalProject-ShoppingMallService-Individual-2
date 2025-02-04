package com.teammeditalk.medicationproject.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.material3.Text
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
import com.teammeditalk.medicationproject.data.repository.AuthRepository
import com.teammeditalk.medicationproject.data.repository.DiseaseRepository
import com.teammeditalk.medicationproject.data.repository.DrugRepository
import com.teammeditalk.medicationproject.data.repository.MyAllergyRepository
import com.teammeditalk.medicationproject.data.repository.MyDiseaseRepository
import com.teammeditalk.medicationproject.data.repository.MyDrugRepository
import com.teammeditalk.medicationproject.ui.Application
import com.teammeditalk.medicationproject.ui.cart.CartScreen
import com.teammeditalk.medicationproject.ui.cart.CartViewModel
import com.teammeditalk.medicationproject.ui.cart.CartViewModelFactory
import com.teammeditalk.medicationproject.ui.detail.DetailViewModel
import com.teammeditalk.medicationproject.ui.detail.DetailViewModelFactory
import com.teammeditalk.medicationproject.ui.detail.DrugDetailInfoScreen
import com.teammeditalk.medicationproject.ui.mypage.MyPageActivity
import com.teammeditalk.medicationproject.ui.order.OrderCompleteScreen
import com.teammeditalk.medicationproject.ui.order.OrderHistoryScreen
import com.teammeditalk.medicationproject.ui.search.drug.SearchDrugScreen
import com.teammeditalk.medicationproject.ui.search.drug.SearchDrugViewModel
import com.teammeditalk.medicationproject.ui.search.drug.SearchDrugViewModelFactory
import com.teammeditalk.medicationproject.ui.search.symptom.SearchDrugBySymptomScreen
import com.teammeditalk.medicationproject.ui.search.symptom.SearchDrugBySymptomViewModel
import com.teammeditalk.medicationproject.ui.search.symptom.SearchDrugBySymptomViewModelFactory
import com.teammeditalk.medicationproject.ui.theme.MedicationProjectTheme
import com.teammeditalk.medicationproject.ui.util.RoundedButton

enum class HomeScreen {
    Home,
    Symptom,
    Search,
    Detail,
    Cart,
    Map,
    Complete,
    OrderDetail,
}

class HomeActivity : ComponentActivity() {
    private val dataStore by lazy { (application as Application).userHealthdataStore }
    private val userInfoDataStore by lazy { (application as Application).userInfoDataStore }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cartViewModel =
            ViewModelProvider(
                this,
                CartViewModelFactory(
                    myAllergyRepository = MyAllergyRepository(dataStore = dataStore),
                    myDiseaseRepository = MyDiseaseRepository(dataStore = dataStore),
                    myDrugRepository = MyDrugRepository(dataStore = dataStore),
                    authRepository = AuthRepository(userInfoDataStore),
                ),
            )[CartViewModel::class.java]

        val viewModel =
            ViewModelProvider(
                this,
                SearchDrugViewModelFactory(DrugRepository()),
            )[SearchDrugViewModel::class.java]

        val searchDrugBySymptomViewModel =
            ViewModelProvider(
                this,
                SearchDrugBySymptomViewModelFactory(DrugRepository()),
            )[SearchDrugBySymptomViewModel::class.java]

        val detailViewModel =
            ViewModelProvider(
                this,
                DetailViewModelFactory(
                    AuthRepository(userInfoDataStore),
                    DiseaseRepository(),
                    MyDiseaseRepository(dataStore = dataStore),
                    MyAllergyRepository(dataStore = dataStore),
                ),
            )[DetailViewModel::class.java]

        setContent {
            var drugInfo by remember { mutableStateOf(emptyList<Item>()) }

            LaunchedEffect(Unit) {
                viewModel.drugInfo.collect {
                    drugInfo = it
                }
            }
            LaunchedEffect(Unit) {
                searchDrugBySymptomViewModel.drugInfo.collect {
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
                                .fillMaxHeight()
                                .padding(innerPadding),
                    ) {
                        composable(route = HomeScreen.Home.name) {
                            HomeScreen(
                                navController = navController,
                                modifier = Modifier.padding(10.dp),
                                onSymptomItemClick = {
                                    navController.navigate(HomeScreen.Home.name)
                                },
                                onPartItemClick = {},
                            )
                        }
                        composable(route = HomeScreen.Symptom.name) {
                            SearchDrugBySymptomScreen(
                                navController = navController,
                                modifier = Modifier,
                                viewmodel = searchDrugBySymptomViewModel,
                            )
                        }
                        composable(route = HomeScreen.Search.name) {
                            SearchDrugScreen(
                                navController = navController,
                                modifier = Modifier,
                                viewmodel = viewModel,
                            )
                        }
                        composable(route = HomeScreen.Detail.name) {
                            DrugDetailInfoScreen(
                                modifier = Modifier,
                                drugInfo = if (drugInfo.isNotEmpty()) drugInfo[0] else Item(),
                                viewModel = detailViewModel,
                                navController = navController,
                            )
                        }
                        composable(route = HomeScreen.Cart.name) {
                            CartScreen(
                                navController = navController,
                                viewModel = cartViewModel,
                            )
                        }

                        composable(route = HomeScreen.Map.name) {
                        }
                        composable(route = HomeScreen.Complete.name) {
                            OrderCompleteScreen(
                                navController = navController,
                            )
                        }
                        composable(route = HomeScreen.OrderDetail.name) {
                            OrderHistoryScreen(viewModel = cartViewModel)
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                modifier
                    .background(MaterialTheme.colorScheme.background),
        ) {
            Row {
                RoundedButton(
                    text = "증상별로\n약 검색하기",
                    imageVector = Icons.Default.Search,
                    onClick = {
                        // todo : 증상 카테고리도 넘기기
                        navController.navigate(HomeScreen.Symptom.name)
                    },
                )

                RoundedButton(
                    text = "약 이름으로\n검색하기",
                    imageVector = Icons.Default.Search,
                    onClick = {
                        // todo : 약 이름 카테고리도 넘기기
                        navController.navigate(HomeScreen.Search.name)
                    },
                )
            }

            Row {
                RoundedButton(
                    text = "방문 가능한\n약국 찾기",
                    imageVector = Icons.Default.LocationOn,
                    onClick = {},
                )

                RoundedButton(
                    text = "나에게 맞는 \n영양제 찾기",
                    imageVector = Icons.Default.Face,
                    onClick = {},
                )
            }
            RoundedButton(
                text = "내 처방 요청 \n히스토리",
                imageVector = Icons.Default.List,
                onClick = {
                    navController.navigate(HomeScreen.OrderDetail.name)
                },
            )

            Spacer(modifier = Modifier.weight(1f))

            // 하단 네비게이션
            BottomNav(navController)
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    private fun BottomNav(
        navController: NavController,
        context: Context = LocalContext.current,
    ) {
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
                onClick = {
                    navController.navigate(HomeScreen.Cart.name)
                },
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
