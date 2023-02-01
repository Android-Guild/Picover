package com.example.android.picover.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

enum class NavigationType {
    NAVIGATION_BAR,
    NAVIGATION_RAIL,
    NAVIGATION_DRAWER;

    companion object {
        fun fromWindowSize(windowSize: WindowSizeClass) = when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Medium -> NAVIGATION_RAIL
            WindowWidthSizeClass.Expanded -> NAVIGATION_DRAWER
            else -> NAVIGATION_BAR
        }
    }
}