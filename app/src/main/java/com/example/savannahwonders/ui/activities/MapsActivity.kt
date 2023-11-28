package com.example.savannahwonders.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.savannahwonders.ui.theme.SavannahWondersTheme

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
                    LocationsScreen(
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

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun LocationsScreen(
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
                            text = "Locations To Visit",
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)
            ) {
                LazyColumn {
                    val locations = listOf(
                        NationalParks(
                            "Maasai Mara National Reserve",
                            "Maasai Mara is known for its exceptional population of lions, leopards, and cheetahs, and the annual migration of zebra, Thomson's gazelle, and wildebeest to and from the Serengeti."
                        ),
                        NationalParks(
                            "Maasai Mara National Reserve",
                            "Maasai Mara is known for its exceptional population of lions, leopards, and cheetahs, and the annual migration of zebra, Thomson's gazelle, and wildebeest to and from the Serengeti."
                        ),
                        NationalParks(
                            "Amboseli National Park",
                            "Amboseli is famous for being the best place in Africa to get close to free-ranging elephants. It offers great views of Mount Kilimanjaro."
                        ),
                        NationalParks(
                            "Amboseli National Park",
                            "Amboseli is famous for being the best place in Africa to get close to free-ranging elephants. It offers great views of Mount Kilimanjaro."
                        ),
                        NationalParks(
                            "Tsavo National Park",
                            "Tsavo is one of the world's largest game reserves, known for its vast herds of dust-red elephants and the famous Tsavo Man-Eaters."
                        ),
                        NationalParks(
                            "Tsavo National Park",
                            "Tsavo is one of the world's largest game reserves, known for its vast herds of dust-red elephants and the famous Tsavo Man-Eaters."
                        ),
                        NationalParks(
                            "Nairobi National Park",
                            "Nairobi National Park is unique, being the only protected area in the world with a variety of animals and birds close to a capital city."
                        ),
                        NationalParks(
                            "Nairobi National Park",
                            "Nairobi National Park is unique, being the only protected area in the world with a variety of animals and birds close to a capital city."
                        )
                    )

                    items(locations) { Location ->
                        LocationCard(Location)
                    }

                }
            }
        }
    }

    data class NationalParks(val name: String, val description: String)

    @Composable
    fun LocationCard(location: NationalParks) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .background(Color.Green)
                .clickable { }
                .clip(RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = location.name,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,

                    )
                Text(
                    text = location.description,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }

        }
    }


    @Preview
    @Composable
    fun LocationsScreenPreview() {
        LocationsScreen(
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




