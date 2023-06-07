package com.intive.picover.main.navigation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.intive.picover.camera.view.CameraScreen
import com.intive.picover.images.view.ImagesScreen
import com.intive.picover.parties.view.PartiesScreen
import com.intive.picover.parties.view.PartyDetailsScreen
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
		composable("parties") {
			PartiesScreen(
				viewModel = hiltViewModel(),
				navController = navController,
			)
		}
		composable("camera") {
			CameraScreen(hiltViewModel())
		}
		composable("profile") {
			ProfileScreen(hiltViewModel())
		}
		composable("images") {
			ImagesScreen(hiltViewModel())
		}
		composable(
			route = "partyDetails/{partyId}",
			arguments = listOf(navArgument("partyId") { type = NavType.IntType }),
		) {
			PartyDetailsScreen(hiltViewModel())
		}
	}
}
