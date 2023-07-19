package net.sevendays.android.code_check.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal class Padding {

    companion object {

        val small: Dp = 8.dp
        val medium: Dp = 12.dp
        val large: Dp = 16.dp

    }

}

internal class FontSize {

    companion object {

        val small: TextUnit = 12.sp
        val medium: TextUnit = 14.sp
        val large: TextUnit = 16.sp

    }

}

internal class ThemeColors {

    companion object {

        @Composable
        fun text() = if (isSystemInDarkTheme()) Color.White else Color.Black

        @Composable
        fun background() = if (isSystemInDarkTheme()) Color.Black else Color.White

    }

}