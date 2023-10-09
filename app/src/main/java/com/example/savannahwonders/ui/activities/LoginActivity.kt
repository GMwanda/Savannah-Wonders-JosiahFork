package com.example.savannahwonders.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.graphics.Typeface.NORMAL
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.savannahwonders.R
import com.example.savannahwonders.ui.theme.SavannahWondersTheme
import com.example.savannahwonders.ui.viewmodels.RegisterViewModel
import com.example.savannahwonders.ui.viewmodels.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val signInViewModel: SignInViewModel by viewModels()
            SavannahWondersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onBackClicked = {
                            finish()
                        },
                        onDontHaveAccountClick = {
                            startActivity(Intent(this, RegisterActivity::class.java))
                        },
                        signInViewModel = signInViewModel,
                        onSuccessfulSignIn = {
                            startActivity(Intent(this, HomeActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onDontHaveAccountClick: ()->Unit,
    signInViewModel: SignInViewModel,
    onSuccessfulSignIn: ()->Unit
) {
    var email: String by rememberSaveable {
        mutableStateOf("")
    }
    var password: String by rememberSaveable {
        mutableStateOf("")
    }
    var isShowPassword: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var isInvalidCredentials: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val state = signInViewModel.signInState.collectAsState(initial = null)
    val context = LocalContext.current
    Scaffold(
        topBar = {
            LoginScreenTopBar(
                onBackClicked = {
                    onBackClicked()
                }
            )
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hey,",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "Welcome Back",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = {
                            Text(text = "Email Address")
                        },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.icons8_mail_48___), contentDescription = "email icon")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(text = "Password")
                        },
                        trailingIcon = {
                            Text(
                                text = if(isShowPassword)"Hide" else "Show",
                                modifier = Modifier
                                    .clickable {
                                        isShowPassword = !isShowPassword
                                        println("Clicked Show password")
                                    },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.icons8_password_45___), contentDescription = "password icon")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                scope.launch {
                                    signInViewModel.loginUser(email, password)
                                }
                            }
                        ),
                        visualTransformation = if(isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(
                        visible = isInvalidCredentials,
                        enter = slideInVertically(spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow))
                    ) {
                        Text(
                            text = "Invalid credentials",
                            fontSize = 15.sp,
                            color = Color.Red
                        )
                    }
                    Text(
                        text = "Forgot your Password?",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                signInViewModel.loginUser(email, password)
                            }
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(20.dp),
                            )
                    ) {
                        Text(text = "LogIn")
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Don't have an account? ")
                        Text(
                            text = "Register",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable { onDontHaveAccountClick() }
                        )
                    }
                }
            }
            LaunchedEffect(key1 = state.value?.isSuccess){
                scope.launch {
                    if(state.value?.isSuccess?.isNotEmpty() == true){
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                        println("SUCCESS MESSAGE $success")
                        onSuccessfulSignIn()
                    }
                }
            }
            LaunchedEffect(key1 = state.value?.isError){
                scope.launch {
                    if(state.value?.isError?.isNotEmpty() == true){
                        val error = state.value?.isError
                        Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                        println("Error MESSAGE $error")
//                        onSuccessfulSignIn()
                        isInvalidCredentials = true
                    }
                }
            }
        }

    }
}

@Composable
fun LoginScreenTopBar(
    modifier:Modifier = Modifier,
    onBackClicked: ()->Unit
) {
    Surface(
        shadowElevation = 4.dp,
        tonalElevation = 2.dp,
        modifier = Modifier
            .height(30.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back"
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    SavannahWondersTheme {
//        LoginScreen(
//            onBackClicked = {
//
//            },
//            onDontHaveAccountClick = {
//
//            },
//            onSuccessfulSignIn = {},
//        )
    }
}