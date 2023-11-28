package com.example.savannahwonders.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savannahwonders.data.model.DestinationModel
import com.example.savannahwonders.data.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val repository: RemoteRepository
) : ViewModel() {
    private var _favoriteScreenUiState: MutableStateFlow<List<DestinationModel>> = MutableStateFlow(
        emptyList()
    )
    val favoriteScreenUiState = _favoriteScreenUiState.asStateFlow()

    fun getFavorites(){
        viewModelScope.launch {
            try {
                val favorites = repository.getFavorites()
                _favoriteScreenUiState.value = favorites
            } catch (e: Exception){
                e.message?.let { Log.e("GET FAVORITES ERROR", it) }
            }

        }
    }
}