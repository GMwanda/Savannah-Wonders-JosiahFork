package com.example.savannahwonders.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.savannahwonders.ui.screens.DestinatioinScreen
import com.example.savannahwonders.ui.screens.FavoritesScreen
import com.example.savannahwonders.ui.screens.HomeScreen
import com.example.savannahwonders.ui.viewmodels.AuthViewModel
import com.example.savannahwonders.ui.viewmodels.DestinationScreenViewModel
import com.example.savannahwonders.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.CoroutineScope

enum class NavGraphDestinations{
    HOME,
    FAVORITES,
    LOGIN,
    REGISTER,
    DESTINATION
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navHostController: NavHostController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    authViewModel: AuthViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    destinationScreenViewModel: DestinationScreenViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = NavGraphDestinations.HOME.name
    ){
        composable(route = NavGraphDestinations.HOME.name){
            HomeScreen(
                drawerState = drawerState,
                scope = coroutineScope,
                onMapsClick = {/*TODO*/},
                authViewModel = authViewModel,
                homeScreenViewModel = homeScreenViewModel,
                destinationScreenViewModel = destinationScreenViewModel,
                navHostController = navHostController
            )
        }
        composable(route = NavGraphDestinations.FAVORITES.name){
            FavoritesScreen(
                navController = navHostController
            )
        }
        composable(route = NavGraphDestinations.DESTINATION.name){
            DestinatioinScreen(
                navHostController = navHostController,
                destinationScreenViewModel = destinationScreenViewModel
            )
        }

    }

}