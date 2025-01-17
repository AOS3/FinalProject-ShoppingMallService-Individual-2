package com.teammeditalk.medicationproject.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.teammeditalk.medicationproject.R
import com.teammeditalk.medicationproject.ui.home.HomeActivity
import com.teammeditalk.medicationproject.ui.theme.MedicationProjectTheme

class AuthActivity : ComponentActivity() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
        { res ->
            this.onSignInResult(res)
        }
    )

    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build(),

        )
    private val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()
    private fun onSignInResult(
        result : FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK){
            val user = FirebaseAuth.getInstance().currentUser
            Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            // todo : 로컬에 user정보 저장하기 이후 자동 로그인 구현
        } else {
            // sign in failed
            Toast.makeText(this,"로그인 실패",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicationProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
                    AuthScreen(
                        modifier = Modifier.padding(innerPadding),
                        onGoogleSignInClick = {
                            signInLauncher.launch(signInIntent)
                        },
                        onGuestSignInClick =  {}
                    )
                }
            }
        }

    }



}


@Composable
fun AuthScreen(
    context : Context = LocalContext.current,
    modifier: Modifier = Modifier,
    onGoogleSignInClick : () -> Unit,
    onGuestSignInClick : () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "간편하게 로그인하고",
            style = MaterialTheme.typography.titleMedium ,
            textAlign = TextAlign.Center
        )
        Text(
            text = "내 몸에 맞는 약을 함께 찾아보아요",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(R.drawable.btn_google_login),
            contentDescription = "google login button",
            modifier = Modifier
                .padding(15.dp)
                .width(300.dp)
                .clickable { onGoogleSignInClick() }

        )
        TextButton(onClick = {
            val intent = Intent(context,HomeActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(
                text = "비회원으로 시작하기",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }


}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MedicationProjectTheme {
        AuthScreen(
            onGoogleSignInClick ={} ,
            onGuestSignInClick = {
            })
    }
}