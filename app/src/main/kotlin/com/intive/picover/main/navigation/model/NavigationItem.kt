package com.intive.picover.main.navigation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.intive.picover.R

enum class NavigationItem(
	@StringRes val labelResId: Int,
	val icon: ImageVector,
	val route: String,
) {
	PARTIES(
		labelResId = R.string.Parties,
		icon = Icons.Filled.Celebration,
		route = "parties",
	),
	PHOTOS(
		labelResId = R.string.Photos,
		icon = Icons.Filled.PhotoCamera,
		route = "photos",
	),
	PROFILE(
		labelResId = R.string.Profile,
		icon = Icons.Filled.Person,
		route = "profileGraph",
	),
	;

	companion object {
		@Composable
		fun NavigationItem.Icon() {
			androidx.compose.material3.Icon(
				imageVector = icon,
				contentDescription = stringResource(id = labelResId),
			)
		}

		@Composable
		fun NavigationItem.Label() {
			Text(text = stringResource(id = labelResId))
		}
	}
}
