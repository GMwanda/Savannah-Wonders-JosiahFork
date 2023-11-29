package com.example.savannahwonders.data.remote

import com.example.savannahwonders.data.model.DestinationModel
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getAllDestinations() : List<DestinationModel>
    suspend fun addToFavorites(destinationModel: DestinationModel)
    suspend fun getFavorites(): List<DestinationModel>
    suspend fun removeFromFavorites(destinationModel: DestinationModel)
    suspend fun search(searchTerm: String): List<DestinationModel>
}