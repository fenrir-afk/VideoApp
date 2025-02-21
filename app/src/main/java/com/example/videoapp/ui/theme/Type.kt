package com.example.videoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.videoapp.R

val goast = FontFamily(
    Font(
        resId = R.font.goast_a_slide,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.goast_a,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.goast_b_slide,
        weight = FontWeight.Thin
    ),
    Font(
        resId = R.font.goast_b,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.solid,
        weight = FontWeight.ExtraBold
    ),

)
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = goast,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = goast,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = goast,
        fontWeight = FontWeight.Normal,
    ),
    headlineMedium = TextStyle(
        fontFamily = goast,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
)