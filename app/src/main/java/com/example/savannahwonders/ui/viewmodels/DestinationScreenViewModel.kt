package com.example.savannahwonders.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savannahwonders.data.model.DestinationModel
import com.example.savannahwonders.data.remote.RemoteRepository
import com.example.savannahwonders.ui.uiStates.Coordinates
import com.example.savannahwonders.ui.uiStates.DestinationScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationScreenViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) :ViewModel() {
    private var _destinationScreenUiState: MutableStateFlow<DestinationScreenUiState> = MutableStateFlow(
        DestinationScreenUiState()
    )
    val destinationScreenUiState = _destinationScreenUiState.asStateFlow()
    private lateinit var selectedDestination: DestinationModel

    fun selectDestination(destinationModel: DestinationModel){
        viewModelScope.launch {
            selectedDestination = destinationModel
            var images = destinationModel.otherImages?.split("  |  ") ?: emptyList()
            var coordinates = destinationModel.location?.split(",")
            var locationCoordinates = coordinates?.let {
                Coordinates(longitude = coordinates[1], latitude = coordinates[0])
            }
            _destinationScreenUiState.value = DestinationScreenUiState(
                name = destinationModel.name,
                location = locationCoordinates,
                images = images,
                description = destinationModel.description,
                rating = destinationModel.rating
            )
        }
    }

    fun addToFavorites(){
        viewModelScope.launch {
            println("Selected Destination: ${selectedDestination.name}")
            remoteRepository.addToFavorites(selectedDestination)
        }
    }
}