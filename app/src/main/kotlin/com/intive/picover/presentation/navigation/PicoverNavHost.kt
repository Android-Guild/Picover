package com.intive.picover.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.intive.picover.presentation.CameraScreen
import com.intive.picover.presentation.HomeScreen
import com.intive.picover.presentation.ProfileScreen

@Composable
fun PicoverNavHost(
	modifier: Modifier,
	navController: NavHostController,
	startDestination: String
) {
	NavHost(
		modifier = modifier,
		navController = navController,
		startDestination = startDestination
	) {
		composable("home") {
			HomeScreen()
		}
		composable("camera") {
			CameraScreen()
		}
		composable("profile") {
			ProfileScreen()
		}
	}
}
