package com.example.savannahwonders.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savannahwonders.data.model.DestinationModel
import com.example.savannahwonders.data.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: RemoteRepository
): ViewModel() {
    private var _searchResults: MutableStateFlow<List<DestinationModel>> = MutableStateFlow(listOf())
    var searchResult = _searchResults.asStateFlow()
    var isSearch: Boolean by mutableStateOf(false)

    fun search(searhString: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchResults.value = repository.search(searhString)
                isSearch = true
            } catch (e: Exception){
                _searchResults.value = emptyList<DestinationModel>()
            }
            println("SEACH SCREEN STATE: ${_searchResults.value.size}")
        }

    }
}