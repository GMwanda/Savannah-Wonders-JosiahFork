package com.example.savannahwonders

import android.content.Intent
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.savannahwonders.ui.theme.SavannahWondersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        }
                    )
                }
            }
        }
    }
}
enum class ScreenNames{
    LOGINSCREEN,
    REGISTRTIONSCREEN
}

@Composable
fun SavannahWondersApp(
    onGetStarted: ()->Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
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

        Button(onClick = onGetStarted) {
            Text(text = "Let's Get Started")
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
            onGetStarted = {}
        )
    }
}