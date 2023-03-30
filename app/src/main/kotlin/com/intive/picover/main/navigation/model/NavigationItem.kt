package com.intive.picover.main.navigation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.ui.graphics.vector.ImageVector
import com.intive.picover.R

sealed class NavigationItem(
	@StringRes val labelResId: Int,
	val icon: ImageVector,
	val route: String,
) {
	object Parties : NavigationItem(
		labelResId = R.string.Parties,
		icon = Icons.Filled.Celebration,
		route = "parties",
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
