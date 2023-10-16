package com.intive.picover.main.navigation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.intive.picover.articles.view.ArticlesScreen
import com.intive.picover.camera.view.CameraScreen
import com.intive.picover.images.view.ImagesScreen
import com.intive.picover.main.navigation.model.NavigationItem
import com.intive.picover.parties.view.PartiesScreen
import com.intive.picover.parties.view.PartyDetailsScreen
import com.intive.picover.profile.view.DeleteAccountDialog
import com.intive.picover.profile.view.ProfileScreen
import com.intive.picover.profile.viewmodel.ProfileViewModel

@Composable
fun PicoverNavHost(
	modifier: Modifier,
	navController: NavHostController,
) {
	NavHost(
		modifier = modifier,
		navController = navController,
		startDestination = NavigationItem.PARTIES.route,
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
			ProfileScreen(hiltViewModel(), navController)
		}
		dialog("profile/deleteAccount") {
			val viewModel: ProfileViewModel = hiltViewModel()
			DeleteAccountDialog(
				onConfirm = {
					viewModel.onDeleteAccountClick()
					navController.popBackStack()
				},
				onDismiss = { navController.popBackStack() },
			)
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
		composable("articles") {
			ArticlesScreen(hiltViewModel())
		}
	}
}
