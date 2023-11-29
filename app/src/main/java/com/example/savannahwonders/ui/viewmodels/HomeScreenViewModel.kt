package com.example.savannahwonders.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savannahwonders.data.model.DestinationModel
import com.example.savannahwonders.data.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
): ViewModel() {

    private var _homeScreenUiState: MutableStateFlow<List<DestinationModel>> = MutableStateFlow(
        emptyList()
    )
    val homeScreenUiState = _homeScreenUiState.asStateFlow()

    init {
        getAllDestinations()
        println("Homesceen viewmodel instantiated successfully.")
    }

    fun getAllDestinations(){
        viewModelScope.launch {
            try {
                val destinations = remoteRepository.getAllDestinations()
                    _homeScreenUiState.value = destinations

//                println(destinations)
                println(_homeScreenUiState.value)
            } catch (e: HttpException){
                e.message?.let { Log.e("GET ALL DESTINATIONS ERROR", it) }
            }


        }
    }

}