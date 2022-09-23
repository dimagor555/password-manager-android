package ru.dimagor555.ui.core.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.dimagor555.ui.core.MR

private val Montserrat = FontFamily(
    MR.fonts.Montserrat.regular.toComposeFont(),
    MR.fonts.Montserrat.medium.toComposeFont(),
    MR.fonts.Montserrat.semibold.toComposeFont(),
    MR.fonts.Montserrat.bold.toComposeFont(),
    MR.fonts.Montserrat.extrabold.toComposeFont()
)

val MontserratTypography = Typography(
    h1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp,
    ),
    h2 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    h3 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    h4 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    h5 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
    ),
    h6 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),
    body1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
)