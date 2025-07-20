package com.camunda.triporganization.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val primaryLight = Color(0xFF6D5E0F)
private val onPrimaryLight = Color(0xFFFFFFFF)
private val primaryContainerLight = Color(0xFFF8E287)
private val onPrimaryContainerLight = Color(0xFF534600)
private val secondaryLight = Color(0xFF665E40)
private val onSecondaryLight = Color(0xFFFFFFFF)
private val secondaryContainerLight = Color(0xFFEEE2BC)
private val onSecondaryContainerLight = Color(0xFF4E472A)
private val tertiaryLight = Color(0xFF43664E)
private val onTertiaryLight = Color(0xFFFFFFFF)
private val tertiaryContainerLight = Color(0xFFC5ECCE)
private val onTertiaryContainerLight = Color(0xFF2C4E38)
private val errorLight = Color(0xFFBA1A1A)
private val onErrorLight = Color(0xFFFFFFFF)
private val errorContainerLight = Color(0xFFFFDAD6)
private val onErrorContainerLight = Color(0xFF93000A)
private val backgroundLight = Color(0xFFFFF9EE)
private val onBackgroundLight = Color(0xFF1E1B13)
private val surfaceLight = Color(0xFFFFF9EE)
private val onSurfaceLight = Color(0xFF1E1B13)
private val surfaceVariantLight = Color(0xFFEAE2D0)
private val onSurfaceVariantLight = Color(0xFF4B4739)
private val outlineLight = Color(0xFF7C7767)
private val outlineVariantLight = Color(0xFFCDC6B4)
private val scrimLight = Color(0xFF000000)
private val inverseSurfaceLight = Color(0xFF333027)
private val inverseOnSurfaceLight = Color(0xFFF7F0E2)
private val inversePrimaryLight = Color(0xFFDBC66E)
private val surfaceDimLight = Color(0xFFE0D9CC)
private val surfaceBrightLight = Color(0xFFFFF9EE)
private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
private val surfaceContainerLowLight = Color(0xFFFAF3E5)
private val surfaceContainerLight = Color(0xFFF4EDDF)
private val surfaceContainerHighLight = Color(0xFFEEE8DA)
private val surfaceContainerHighestLight = Color(0xFFE8E2D4)

private val primaryDark = Color(0xFFDBC66E)
private val onPrimaryDark = Color(0xFF3A3000)
private val primaryContainerDark = Color(0xFF534600)
private val onPrimaryContainerDark = Color(0xFFF8E287)
private val secondaryDark = Color(0xFFD1C6A1)
private val onSecondaryDark = Color(0xFF363016)
private val secondaryContainerDark = Color(0xFF4E472A)
private val onSecondaryContainerDark = Color(0xFFEEE2BC)
private val tertiaryDark = Color(0xFFA9D0B3)
private val onTertiaryDark = Color(0xFF143723)
private val tertiaryContainerDark = Color(0xFF2C4E38)
private val onTertiaryContainerDark = Color(0xFFC5ECCE)
private val errorDark = Color(0xFFFFB4AB)
private val onErrorDark = Color(0xFF690005)
private val errorContainerDark = Color(0xFF93000A)
private val onErrorContainerDark = Color(0xFFFFDAD6)
private val backgroundDark = Color(0xFF15130B)
private val onBackgroundDark = Color(0xFFE8E2D4)
private val surfaceDark = Color(0xFF15130B)
private val onSurfaceDark = Color(0xFFE8E2D4)
private val surfaceVariantDark = Color(0xFF4B4739)
private val onSurfaceVariantDark = Color(0xFFCDC6B4)
private val outlineDark = Color(0xFF969080)
private val outlineVariantDark = Color(0xFF4B4739)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFE8E2D4)
private val inverseOnSurfaceDark = Color(0xFF333027)
private val inversePrimaryDark = Color(0xFF6D5E0F)
private val surfaceDimDark = Color(0xFF15130B)
private val surfaceBrightDark = Color(0xFF3C3930)
private val surfaceContainerLowestDark = Color(0xFF100E07)
private val surfaceContainerLowDark = Color(0xFF1E1B13)
private val surfaceContainerDark = Color(0xFF222017)
private val surfaceContainerHighDark = Color(0xFF2D2A21)
private val surfaceContainerHighestDark = Color(0xFF38352B)


object Colors {

    val primary: Color @Composable get() = if (isSystemInDarkTheme()) primaryDark else primaryLight
    val onPrimary: Color @Composable get() = if (isSystemInDarkTheme()) onPrimaryDark else onPrimaryLight
    val primaryContainer: Color @Composable get() = if (isSystemInDarkTheme()) primaryContainerDark else primaryContainerLight
    val onPrimaryContainer: Color @Composable get() = if (isSystemInDarkTheme()) onPrimaryContainerDark else onPrimaryContainerLight

    val secondary: Color @Composable get() = if (isSystemInDarkTheme()) secondaryDark else secondaryLight
    val onSecondary: Color @Composable get() = if (isSystemInDarkTheme()) onSecondaryDark else onSecondaryLight
    val secondaryContainer: Color @Composable get() = if (isSystemInDarkTheme()) secondaryContainerDark else secondaryContainerLight
    val onSecondaryContainer: Color @Composable get() = if (isSystemInDarkTheme()) onSecondaryContainerDark else onSecondaryContainerLight

    val tertiary: Color @Composable get() = if (isSystemInDarkTheme()) tertiaryDark else tertiaryLight
    val onTertiary: Color @Composable get() = if (isSystemInDarkTheme()) onTertiaryDark else onTertiaryLight
    val tertiaryContainer: Color @Composable get() = if (isSystemInDarkTheme()) tertiaryContainerDark else tertiaryContainerLight
    val onTertiaryContainer: Color @Composable get() = if (isSystemInDarkTheme()) onTertiaryContainerDark else onTertiaryContainerLight

    val error: Color @Composable get() = if (isSystemInDarkTheme()) errorDark else errorLight
    val onError: Color @Composable get() = if (isSystemInDarkTheme()) onErrorDark else onErrorLight
    val errorContainer: Color @Composable get() = if (isSystemInDarkTheme()) errorContainerDark else errorContainerLight
    val onErrorContainer: Color @Composable get() = if (isSystemInDarkTheme()) onErrorContainerDark else onErrorContainerLight

    val background: Color @Composable get() = if (isSystemInDarkTheme()) backgroundDark else backgroundLight
    val onBackground: Color @Composable get() = if (isSystemInDarkTheme()) onBackgroundDark else onBackgroundLight
    val surface: Color @Composable get() = if (isSystemInDarkTheme()) surfaceDark else surfaceLight
    val onSurface: Color @Composable get() = if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight
    val surfaceVariant: Color @Composable get() = if (isSystemInDarkTheme()) surfaceVariantDark else surfaceVariantLight
    val onSurfaceVariant: Color @Composable get() = if (isSystemInDarkTheme()) onSurfaceVariantDark else onSurfaceVariantLight

    val outline: Color @Composable get() = if (isSystemInDarkTheme()) outlineDark else outlineLight
    val outlineVariant: Color @Composable get() = if (isSystemInDarkTheme()) outlineVariantDark else outlineVariantLight
    val scrim: Color @Composable get() = if (isSystemInDarkTheme()) scrimDark else scrimLight
    val inverseSurface: Color @Composable get() = if (isSystemInDarkTheme()) inverseSurfaceDark else inverseSurfaceLight
    val inverseOnSurface: Color @Composable get() = if (isSystemInDarkTheme()) inverseOnSurfaceDark else inverseOnSurfaceLight
    val inversePrimary: Color @Composable get() = if (isSystemInDarkTheme()) inversePrimaryDark else inversePrimaryLight

    val surfaceDim: Color @Composable get() = if (isSystemInDarkTheme()) surfaceDimDark else surfaceDimLight
    val surfaceBright: Color @Composable get() = if (isSystemInDarkTheme()) surfaceBrightDark else surfaceBrightLight
    val surfaceContainerLowest: Color @Composable get() = if (isSystemInDarkTheme()) surfaceContainerLowestDark else surfaceContainerLowestLight
    val surfaceContainerLow: Color @Composable get() = if (isSystemInDarkTheme()) surfaceContainerLowDark else surfaceContainerLowLight
    val surfaceContainer: Color @Composable get() = if (isSystemInDarkTheme()) surfaceContainerDark else surfaceContainerLight
    val surfaceContainerHigh: Color @Composable get() = if (isSystemInDarkTheme()) surfaceContainerHighDark else surfaceContainerHighLight
    val surfaceContainerHighest: Color @Composable get() = if (isSystemInDarkTheme()) surfaceContainerHighestDark else surfaceContainerHighestLight

}


private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Composable
fun TripOrganizationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}





