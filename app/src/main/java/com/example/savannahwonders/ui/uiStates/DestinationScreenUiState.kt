package com.example.savannahwonders.ui.uiStates

import com.example.savannahwonders.data.model.DestinationModel

data class DestinationScreenUiState(
    var name: String ?= "",
    var location: Coordinates ?= Coordinates(),
    var images: List<String> ?= emptyList(),
    var rating: Double ?= 0.0,
    var description: String ?= ""
)
data class Coordinates(
    var longitude: String = "0",
    var latitude: String = "0"
)
