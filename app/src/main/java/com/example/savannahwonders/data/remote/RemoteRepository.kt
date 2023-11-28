package com.example.savannahwonders.data.remote

import com.example.savannahwonders.data.model.DestinationModel
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getAllDestinations() : List<DestinationModel>
    suspend fun testDb()
}