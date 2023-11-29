package com.example.savannahwonders.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.savannahwonders.R


val nunito = FontFamily(
    Font(R.font.nunito_variablefont_wght),
    Font(R.font.nunito_italic_variablefont_wght)
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = nunito,
    ),
    /* Other default text styles to override */
    titleLarge = TextStyle(
        fontFamily = nunito,
    ),
    labelSmall = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 12.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = nunito,

        )

)