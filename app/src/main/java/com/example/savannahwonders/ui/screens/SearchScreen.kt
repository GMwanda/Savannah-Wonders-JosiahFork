package com.example.savannahwonders.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.savannahwonders.data.model.DestinationModel
import com.example.savannahwonders.ui.navigation.NavGraphDestinations
import com.example.savannahwonders.ui.viewmodels.DestinationScreenViewModel
import com.example.savannahwonders.ui.viewmodels.SearchScreenViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel,
    navHostController: NavHostController,
    destinationScreenViewModel: DestinationScreenViewModel,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    var searchValue by rememberSaveable {
        mutableStateOf("")
    }
    val uiState = searchScreenViewModel.searchResult.collectAsState()
    var isSearch = searchScreenViewModel.isSearch
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (isSearch) "Search Result for '$searchValue'" else "Search",
                        fontSize = 17.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if(isSearch) {
                                searchScreenViewModel.isSearch = false
                            } else {
                                navHostController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                },
                modifier = Modifier
                    .height(35.dp)
            )
        },
    ) {
        if (isSearch) {
            if (uiState.value.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 50.dp)
                        .fillMaxSize()
                ) {
                    items(uiState.value) { item: DestinationModel ->
                        Surface(
                            tonalElevation = 2.dp,
                            shape = RoundedCornerShape(20.dp),
                            shadowElevation = 10.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable {
                                    destinationScreenViewModel.selectDestination(item)
                                    navHostController.navigate(NavGraphDestinations.DESTINATION.name)
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AsyncImage(
                                    model = item.mainImage?.let { it },
                                    contentDescription = "Image",
                                    modifier = Modifier
                                        .size(180.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                ) {
                                    item.name?.let { it1 ->
                                        Text(
                                            text = it1,
                                            fontSize = 20.sp,
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    item.description?.let { it1 -> Text(text = it1) }
                                }
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "No products found :(",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .fillMaxWidth()
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 35.dp, start = 8.dp, end = 8.dp, bottom = 50.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    },
                    placeholder = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        searchScreenViewModel.search(searchValue)
                    }),
                    shape = RoundedCornerShape(15.dp),
//                    modifier = Modifier
//                        .width(320.dp)
                )
                Spacer(modifier = Modifier.height(35.dp))
                Text(
                    text = "Recent Search results",
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}