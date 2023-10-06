package com.example.savannahwonders.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savannahwonders.data.auth.AuthRepository
import com.example.savannahwonders.data.auth.Resource
import com.example.savannahwonders.data.auth.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    val _signInState = Channel<SignInState>()
    var signInState = _signInState.receiveAsFlow()
    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            repository.loginUser(email, password).collect{result ->
                when (result){
                    is Resource.Success -> {
                        _signInState.send(SignInState(isSuccess = "Sign In Successful"))
                    }
                    is Resource.Loading -> {
                        _signInState.send(SignInState(isLoading = true))
                    }
                    is Resource.Error  -> {
                        _signInState.send(SignInState(isError = result.message))
                        println("SIGNIN ERROR!! ${result.message}")
                    }
                }
            }
        }
    }

}