package com.example.savannahwonders

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.savannahwonders.ui.activities.HomeActivity
import com.example.savannahwonders.ui.activities.LoginActivity
import com.example.savannahwonders.ui.activities.RegisterActivity
import com.example.savannahwonders.ui.theme.SavannahWondersTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = auth.currentUser

        if (user == null){
            setContent {
                SavannahWondersTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SavannahWondersApp(
                            onGetStarted = {
                                startActivity(Intent(this, RegisterActivity::class.java))
                            },
                            onHaveAccountClick = {
                                startActivity(Intent(this, LoginActivity::class.java))
                            }
                        )
                    }
                }
            }
        } else {
            setContent {
                SavannahWondersTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun SavannahWondersApp(
    onGetStarted: ()->Unit,
    onHaveAccountClick: ()->Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(0.dp)
        ) {
            Text(
                text = "Savannah",
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Wonders",
                fontSize = 15.sp,
                letterSpacing = 5.sp,
                fontWeight = FontWeight.Light
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .size(550.dp)
                .padding(horizontal = 5.dp),
            shadowElevation = 10.dp,
        ) {
            Image(
                painter = painterResource(id = R.drawable.elephants),
                contentDescription = "Elephants",
                contentScale = ContentScale.FillBounds,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onGetStarted,
            modifier = Modifier
                .width(200.dp)
                .shadow(10.dp, RoundedCornerShape(20.dp))
        ) {
            Text(text = "Let's Get Started")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account? ")
            Text(
                text = "Login",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {
                        onHaveAccountClick()
                    }
            )
        }

    }
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = ScreenNames.LOGINSCREEN.name
//    ){
//        composable(
//            route = ScreenNames.LOGINSCREEN.name
//        ){
//            LoginScreen(
////                navController = navController
//            )
//        }
//        composable(
//            route = ScreenNames.REGISTRTIONSCREEN.name
//        ){
//            RegisterScreen()
//        }
//    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SavannahWondersTheme {
        SavannahWondersApp(
            onGetStarted = {},
            onHaveAccountClick = {}
        )
    }
}