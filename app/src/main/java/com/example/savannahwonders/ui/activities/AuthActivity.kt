package com.example.savannahwonders.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.savannahwonders.ui.navigation.NavGraphDestinations
import com.example.savannahwonders.ui.screens.LoginScreen
import com.example.savannahwonders.ui.screens.RegisterScreen
import com.example.savannahwonders.ui.theme.SavannahWondersTheme
import com.example.savannahwonders.ui.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            SavannahWondersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthNavigation(authViewModel = authViewModel)
                }
            }
        }
    }
}

@Composable
fun AuthNavigation(
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = NavGraphDestinations.LOGIN.name){
        composable(route = NavGraphDestinations.LOGIN.name){
            LoginScreen(
                onDontHaveAccountClick = { navController.navigate(NavGraphDestinations.REGISTER.name) },
                authViewModel = authViewModel,
            )
        }
        composable(route = NavGraphDestinations.REGISTER.name){
            RegisterScreen(
                onHaveAccountClick = { navController.navigate(NavGraphDestinations.LOGIN.name) },
                authViewModel = authViewModel,
            )
        }
    }
}
