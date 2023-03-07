package com.intive.picover.main.navigation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.ui.graphics.vector.ImageVector
import com.intive.picover.R

sealed class NavigationItem(
	@StringRes val labelResId: Int,
	val icon: ImageVector,
	val route: String,
) {
	object Home : NavigationItem(
		labelResId = R.string.Home,
		icon = Icons.Filled.Home,
		route = "home",
	)

	object Camera : NavigationItem(
		labelResId = R.string.Camera,
		icon = Icons.Filled.PhotoCamera,
		route = "camera",
	)

	object Profile : NavigationItem(
		labelResId = R.string.Profile,
		icon = Icons.Filled.Person,
		route = "profile",
	)
}
