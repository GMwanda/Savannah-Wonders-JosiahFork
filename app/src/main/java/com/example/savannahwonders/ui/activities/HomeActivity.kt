package com.example.savannahwonders.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.savannahwonders.MainActivity
import com.example.savannahwonders.data.temp.Destination
import com.example.savannahwonders.data.temp.TempData
import com.example.savannahwonders.ui.theme.SavannahWondersTheme
import com.example.savannahwonders.ui.viewmodels.RegisterViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val registerViewModel: RegisterViewModel by viewModels()
        super.onCreate(savedInstanceState)
        setContent {
            SavannahWondersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreenActivity(
                        onLogOut = {
                            registerViewModel.logOut()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        },
                        onSearchClick = {
                            // TODO: Launch Search screen
                        },
                        onHomeClick = {

                        },
                        onFavoritesClick = {
                            startActivity(Intent(this, FavoritesActivity::class.java))
                            finish()
                        },
                        onMapsClick = {
                            startActivity(Intent(this, MapsActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenActivity(
    onLogOut: () -> Unit,
    onFavoritesClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMapsClick: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isActive: Int by rememberSaveable {
        mutableStateOf(1)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home",
                        fontSize = 17.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon",
                            modifier = Modifier
                                .size(27.dp)
                        )
                    }

                },
                modifier = Modifier
                    .height(40.dp)
            )
        },
        bottomBar = {
            BottomAppBar(
                onFavoritesClick = {
                    onFavoritesClick()
                    isActive = 1
                },
                onHomeClick = {
                    onHomeClick()
                    isActive = 2
                },
                onSearchClick = {
                    onSearchClick()
                    isActive = 3
                },

                isActive = isActive
            )
        }
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                val selectedItem = remember { mutableStateOf(menuItems[0].id) }
                ModalDrawerSheet(
                    modifier = Modifier
                        .width(258.dp)
                ) {
                    Spacer(Modifier.height(52.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User Icon",
                            modifier = Modifier
                                .size(72.dp)
                        )
                        Firebase.auth.currentUser?.email?.let { it1 ->
                            Text(
                                text = it1,
                                fontWeight = FontWeight.Light,
                                fontSize = 13.sp
                            )
                        }
                    }
                    Spacer(Modifier.height(52.dp))
                    menuItems.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = item.description) },
                            label = { Text(item.title) },
                            selected = item.id == selectedItem.value,
                            onClick = {
                                selectedItem.value = item.id
                                scope.launch {
                                    drawerState.close()
                                    if (item.id == 5) {
                                        onLogOut()
                                    }
                                    if (item.id == 3) {
                                        onMapsClick()
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            },
            content = {
                HomeScreen()
            }
        )
    }
}

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(top = 50.dp)
    ) {
        Box {
            LazyColumn {
                items(TempData.listOfDestinations) { item: Destination ->
                    Surface(
                        tonalElevation = 2.dp,
                        shape = RoundedCornerShape(20.dp),
                        shadowElevation = 10.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = item.image),
                                contentDescription = "Destination Image",
                                modifier = Modifier
                                    .size(80.dp)
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .fillMaxHeight()
                            ) {
                                Text(text = item.name)
                                Text(text = "Ksh. ${item.price}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomAppBar(
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onHomeClick: () -> Unit,
    isActive: Int
) {
    var homeIcon: ImageVector by remember {
        mutableStateOf(Icons.Default.Home)
    }
    var searchIcon: ImageVector by remember {
        mutableStateOf(Icons.Default.Search)
    }
    var favoritesIcon: ImageVector by remember {
        mutableStateOf(Icons.Default.FavoriteBorder)
    }

    when (isActive) {
        1 -> {
            homeIcon = Icons.Filled.Home
            searchIcon = Icons.Outlined.Search
            favoritesIcon = Icons.Outlined.FavoriteBorder
        }

        2 -> {
            homeIcon = Icons.Outlined.Home
            searchIcon = Icons.Filled.Search
            favoritesIcon = Icons.Outlined.FavoriteBorder
        }

        3 -> {
            homeIcon = Icons.Outlined.Home
            searchIcon = Icons.Outlined.Search
            favoritesIcon = Icons.Filled.Favorite
        }
    }
    androidx.compose.material3.BottomAppBar(
        windowInsets = WindowInsets.navigationBars,
        contentPadding = PaddingValues(horizontal = 2.dp),
        tonalElevation = 2.dp,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    onHomeClick()
                },
                modifier = Modifier
                    .size(55.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Icon(
                        imageVector = homeIcon,
                        contentDescription = "Home Icon",
                        modifier = Modifier
                            .size(27.dp)
                    )
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
            IconButton(
                onClick = {
                    onSearchClick()
                },
                modifier = Modifier
                    .size(55.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Icon(
                        imageVector = searchIcon,
                        contentDescription = "Search Icon",
                        modifier = Modifier
                            .size(27.dp)
                    )
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
            IconButton(
                onClick = {
                    onFavoritesClick()
                },
                modifier = Modifier
                    .size(55.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {

                    Icon(
                        imageVector = favoritesIcon,
                        contentDescription = "Favorites Icon",
                        modifier = Modifier
                            .size(27.dp)
                    )
                    Text(
                        text = "Favorites",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
        }
    }
}

data class MenuItem(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val description: String
)

val menuItems: List<MenuItem> = listOf(
    MenuItem(
        id = 1,
        title = "Home",
        icon = Icons.Default.Home,
        description = "Home Icon"
    ),
    MenuItem(
        id = 2,
        title = "Settings",
        icon = Icons.Default.Settings,
        description = "Settings Icon"
    ),
    MenuItem(
        id = 3,
        title = "Map of Kenya",
        icon = Icons.Sharp.Close,
        description = "Logout Icon"
    ),
    MenuItem(
        id = 4,
        title = "Favorites",
        icon = Icons.Default.Favorite,
        description = "Cart Icon"
    ),
    MenuItem(
        id = 5,
        title = "Logout",
        icon = Icons.Sharp.Close,
        description = "Logout Icon"
    )
)

@Preview
@Composable
fun HomeScreenActivityPreview() {
    SavannahWondersTheme {
        HomeScreenActivity(
            onLogOut = {},
            onFavoritesClick = {},
            onHomeClick = {},
            onSearchClick = {},
            onMapsClick = {},
        )
    }
}