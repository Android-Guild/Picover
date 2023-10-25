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
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.intive.picover.articles.view.ArticlesScreen
import com.intive.picover.images.view.ImagesScreen
import com.intive.picover.main.navigation.model.NavigationItem
import com.intive.picover.parties.view.AddPartyBottomSheet
import com.intive.picover.parties.view.PartiesScreen
import com.intive.picover.parties.view.PartyDetailsScreen
import com.intive.picover.profile.view.DeleteAccountDialog
import com.intive.picover.profile.view.ProfileScreen
import com.intive.picover.profile.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun PicoverNavHost(
	modifier: Modifier,
	navController: NavHostController,
	bottomSheetNavigator: BottomSheetNavigator,
) {
	ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
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
			bottomSheet(route = "parties/addParty") {
				AddPartyBottomSheet(
					viewModel = hiltViewModel(),
					navController = navController,
				)
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
			composable("photos") {
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
}
