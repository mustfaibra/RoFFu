package com.mustfaibra.roffu.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mustfaibra.roffu.R

val font = FontFamily(
    Font(resId = R.font.poppins_light, weight = FontWeight.ExtraLight, style = FontStyle.Normal),
    Font(resId = R.font.poppins_black, weight = FontWeight.Black, style = FontStyle.Normal),
    Font(resId = R.font.poppins_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(resId = R.font.poppin_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(resId = R.font.poppins_bold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(resId = R.font.poppins_extra_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 35.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 25.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 15.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    defaultFontFamily = font,
)