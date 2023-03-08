package com.intive.picover.main.navigation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.intive.picover.camera.view.CameraScreen
import com.intive.picover.home.view.HomeScreen
import com.intive.picover.profile.view.ProfileScreen

@Composable
fun PicoverNavHost(
	modifier: Modifier,
	navController: NavHostController,
	startDestination: String,
) {
	NavHost(
		modifier = modifier,
		navController = navController,
		startDestination = startDestination,
	) {
		composable("home") {
			HomeScreen()
		}
		composable("camera") {
			CameraScreen(hiltViewModel())
		}
		composable("profile") {
			ProfileScreen()
		}
	}
}
