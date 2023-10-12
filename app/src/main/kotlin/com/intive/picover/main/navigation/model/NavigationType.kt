package com.intive.picover.main.navigation.model

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

enum class NavigationType {
	BOTTOM_BAR,
	RAIL,
	;

	companion object {
		fun fromWindowSize(windowSize: WindowSizeClass) =
			if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
				BOTTOM_BAR
			} else {
				RAIL
			}
	}
}
