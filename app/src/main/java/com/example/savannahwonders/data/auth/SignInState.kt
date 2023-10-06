package com.example.savannahwonders.data.auth

data class SignInState(
    val isLoading:Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)
