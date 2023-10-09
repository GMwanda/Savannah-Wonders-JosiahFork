package com.example.savannahwonders.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.savannahwonders.ui.activities.ui.theme.SavannahWondersTheme

class MapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SavannahWondersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MapsScreen(
                        onBackClick = {
                            finish()
                        },
                        onSearchClick = {
                            // TODO: Launch Search screen
                        },
                        onHomeClick = {
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        },
                        onFavoritesClick = {

                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapsScreen(
    onBackClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    var isActive: Int by rememberSaveable {
        mutableStateOf(3)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Map of Kenya",
                        fontSize = 17.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        ) {
//
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    SavannahWondersTheme {
        MapsScreen(
            onBackClick = { /*TODO*/ },
            onFavoritesClick = { /*TODO*/ },
            onHomeClick = { /*TODO*/ }) {

        }
    }
}

